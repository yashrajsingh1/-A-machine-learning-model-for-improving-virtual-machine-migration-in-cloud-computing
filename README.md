# A Machine Learning Model for Improving Virtual Machine Migration in Cloud Computing

This project demonstrates how Machine Learning (ML) can be used to optimize Virtual Machine (VM) migration in cloud computing environments. By predicting resource usage (CPU, memory, and network), the system proactively manages VMs to reduce energy consumption, minimize migration overhead, and maintain service quality.


##  Project Structure
├── code/
│ ├── MLClient.java
│ ├── VmMigrationOptimizer.java
│ ├── VmMigrationBaseline.java
│ └── MLVmMigrationSimulation.java
├── ml_model/
│ ├── rf_cpu_usage.joblib
│ ├── rf_memory_usage.joblib
│ └── rf_network_traffic.joblib
├── saved_models/
│ ├── lgbm_cpu_usage.joblib
│ ├── lgbm_memory_usage.joblib
│ └── lgbm_network_traffic.joblib
├── results/
│ ├── results_with_ml.csv
│ └── results_without_ml.csv
├── graphs/
│ └── comparison_plot.png
├── report/
│ ├── Major_Project_Report_Yashraj.pdf
│ └── Thesis.docx
└── README.md
##  Objective

- Predict future VM resource demands (CPU, memory, and network usage) using ML.
- Use the prediction to make smarter VM migration decisions.
- Compare performance against baseline (non-ML) migration strategies.

##  Technologies Used

| Stack           | Tools/Frameworks                |
|----------------|----------------------------------|
| Programming     | Java (CloudSim), Python (ML)     |
| ML Algorithms   | LightGBM, Random Forest          |
| Libraries       | scikit-learn, pandas, matplotlib |
| Simulation Tool | CloudSim 3.0.3 (Java)            |

# Machine Learning Model API for VM Migration

This folder contains the trained LightGBM models and a Flask API used to predict VM resource utilization (CPU, memory, and network traffic) to assist with efficient virtual machine migration decisions in cloud computing.

---

##  Contents

- `app.py` - Flask application that serves predictions for CPU, memory, and network traffic.
- `lgbm_cpu_usage.joblib` - Trained LightGBM model for CPU usage prediction.
- `lgbm_memory_usage.joblib` - Trained LightGBM model for memory usage prediction.
- `lgbm_network_traffic.joblib` - Trained LightGBM model for network traffic prediction.
- `requirements.txt` - Required Python packages to run the API.

---

##  Running the API

### 1. Install dependencies
```bash
pip install -r requirements.txt

 code/ — Java CloudSim Simulation Files
This folder contains all the Java source files used to simulate virtual machine (VM) migration scenarios in a cloud environment using CloudSim 3.0.3. It includes both a baseline (non-ML) and an ML-optimized implementation.

 Files Description
File Name	Purpose
MLClient.java	Acts as a bridge between the CloudSim simulation and the ML prediction Flask API. It sends input features (like CPU, memory usage) and receives predicted usage values for decision-making.
VmMigrationOptimizer.java	Main simulation file for the ML-based VM migration strategy. It uses predictions from MLClient.java to decide whether to migrate or create new VMs. Also collects and saves metrics like energy, migrations, and active servers.
VmMigrationBaseline.java	A simpler version of the migration logic that doesn’t use ML. This serves as a baseline for performance comparison (energy, VM usage, etc.).
MLVmMigrationSimulation.java	(Optional/extra) Can be used as a wrapper if needed to test multiple ML configurations or scenarios.

 How It Works
The simulations are powered by CloudSim 3.0.3.

MLClient.java uses HttpURLConnection to communicate with the Python Flask API running locally (http://127.0.0.1:5000/predict).

VmMigrationOptimizer and VmMigrationBaseline differ only in how VM creation decisions are made:

ML-based version uses model predictions.

Baseline version uses random or static thresholds.

 Output
Each simulation logs:

VM creation decisions

Cloudlet execution details

Number of migrations

Energy consumption

Active server count

The simulation writes results to:

bash
Copy code
/results/results_with_ml.csv
/results/results_without_ml.csv
 How to Run
Make sure your MLClient.java is connected to a running Python Flask API.

Compile the Java project via Eclipse or Maven.

Run either:

VmMigrationOptimizer.java for the ML-enhanced simulation.

VmMigrationBaseline.java for baseline logic.

##  Results

| Metric             | With ML         | Without ML      |
|--------------------|------------------|------------------|
| Energy Consumption |  Lower          |  Higher         |
| VM Migrations      |  Reduced        |  More frequent  |
| Active Servers     |  Optimized usage|  Under/Over-used|

Graphical comparison is available in the `/graphs` folder.

##  How to Run

### ➤ Java Simulation (CloudSim)
1. Open Eclipse or IntelliJ
2. Import `cloudsim` project
3. Place `MLClient.java` and `VmMigrationOptimizer.java` in your Java source path
4. Run `VmMigrationOptimizer.java`

### ➤ Python ML API
1. Navigate to `/python_api/`
2. Install dependencies:  


