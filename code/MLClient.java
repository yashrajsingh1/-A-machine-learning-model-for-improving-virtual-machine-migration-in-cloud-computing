package org.cloudbus.cloudsim.examples;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MLClient {
    public static double[] getPredictions(double[] features) throws Exception {
        String jsonInput = "{\"features\": [[" +
                features[0] + ", " +
                features[1] + ", " +
                features[2] + ", " +
                features[3] + ", " +
                features[4] + ", " +
                features[5] + ", " +
                features[6] + ", " +
                features[7] + "]]}";

        URL url = new URL("http://127.0.0.1:5000/predict");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes());
            os.flush();
        }

        Scanner sc = new Scanner(conn.getInputStream());
        String response = "";
        while (sc.hasNext()) {
            response += sc.nextLine();
        }
        sc.close();

        // Basic JSON parsing (quick and dirty)
        double cpu = Double.parseDouble(response.split("cpu_usage\":")[1].split(",")[0]);
        double mem = Double.parseDouble(response.split("memory_usage\":")[1].split(",")[0]);
        double net = Double.parseDouble(response.split("network_traffic\":")[1].split("}")[0]);

        return new double[]{cpu, mem, net};
    }
}
