package org.cloudbus.cloudsim.examples;
import org.cloudbus.cloudsim.examples.MLClient;


import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class VmMigrationOptimizer {

    public static void main(String[] args) {
        Log.printLine("Starting VM Migration Optimizer Simulation...");

        try {
            int numUsers = 1;
            Calendar calendar = Calendar.getInstance();
            boolean traceFlag = false;
            CloudSim.init(numUsers, calendar, traceFlag);

            Datacenter datacenter0 = createDatacenter("Datacenter_0");
            DatacenterBroker broker = new DatacenterBroker("Broker");
            int brokerId = broker.getId();

            List<Vm> vmlist = new ArrayList<>();
            List<Cloudlet> cloudletList = new ArrayList<>();
            double totalEnergy = 0;
            int migratedVMs = 0;

            FileWriter csvWriter = new FileWriter("results_with_ml.csv");
            csvWriter.append("Time,ActiveServers,MigratedVMs,Energy\n");

            int vmid = 0;
            int cloudletId = 0;
            int mips = 1000, ram = 512, pesNumber = 1;
            long size = 10000, bw = 1000;
            String vmm = "Xen";

            for (int time = 0; time < 10; time++) {
                double[] inputFeatures = {
                    100 + time * 5,
                    2000 + time * 10,
                    1.0 + 0.1 * time,
                    80 + time,
                    (time % 24),
                    (time % 7),
                    50 + time * 2,
                    60 + time * 3
                };

                try {
                    double[] predictions = MLClient.getPredictions(inputFeatures);
                    double predictedCpu = predictions[0];
                    double predictedMem = predictions[1];

                    if (predictedCpu < 0.8 && predictedMem < 0.8) {
                        Vm vm = new Vm(vmid++, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
                        vmlist.add(vm);
                        migratedVMs++;

                        double avgPower = 200;
                        double migrationTime = 5;
                        double energy = (avgPower * migrationTime) / 3600.0;
                        totalEnergy += energy;

                        Cloudlet cloudlet = new Cloudlet(cloudletId++, 40000, pesNumber, 300, 300,
                                new UtilizationModelFull(), new UtilizationModelFull(), new UtilizationModelFull());
                        cloudlet.setUserId(brokerId);
                        cloudletList.add(cloudlet);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Count active servers
                int activeServers = 0;
                for (Host host : datacenter0.getHostList()) {
                    if (!host.getVmList().isEmpty()) {
                        activeServers++;
                    }
                }

                csvWriter.append(time + "," + activeServers + "," + migratedVMs + "," + totalEnergy + "\n");
            }

            broker.submitVmList(vmlist);
            broker.submitCloudletList(cloudletList);

            CloudSim.startSimulation();
            CloudSim.stopSimulation();

            csvWriter.flush();
            csvWriter.close();

            Log.printLine("ML Simulation complete. Data written to results_with_ml.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Datacenter createDatacenter(String name) throws Exception {
        List<Host> hostList = new ArrayList<>();
        List<Pe> peList = new ArrayList<>();
        peList.add(new Pe(0, new PeProvisionerSimple(1000)));

        for (int i = 0; i < 5; i++) {
            Host host = new Host(i,
                    new RamProvisionerSimple(2048),
                    new BwProvisionerSimple(10000),
                    1000000,
                    peList,
                    new VmSchedulerTimeShared(peList));
            hostList.add(host);
        }

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                "x86", "Linux", "Xen", hostList,
                10.0, 3.0, 0.05, 0.001, 0.0);

        return new Datacenter(name, characteristics,
                new VmAllocationPolicySimple(hostList), new LinkedList<>(), 0);
    }
}
