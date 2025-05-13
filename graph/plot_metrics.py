import pandas as pd
import matplotlib.pyplot as plt

# Load both files
ml = pd.read_csv("results_with_ml.csv")
baseline = pd.read_csv("results_without_ml.csv")

plt.figure(figsize=(16, 6))

# --- ENERGY ---
plt.subplot(1, 3, 1)
plt.plot(ml['Time'], ml['Energy'], label='With ML', marker='o')
plt.plot(baseline['Time'], baseline['Energy'], label='Baseline', marker='x')
plt.title("Energy Consumption")
plt.xlabel("Time")
plt.ylabel("Energy (kWh)")
plt.legend()
plt.grid(True)

# --- MIGRATIONS ---
plt.subplot(1, 3, 2)
plt.plot(ml['Time'], ml['MigratedVMs'], label='With ML', marker='o', color='green')
plt.plot(baseline['Time'], baseline['MigratedVMs'], label='Baseline', marker='x', color='orange')
plt.title("VM Migrations")
plt.xlabel("Time")
plt.ylabel("Migrations")
plt.legend()
plt.grid(True)

# --- ACTIVE SERVERS ---
plt.subplot(1, 3, 3)
plt.plot(ml['Time'], ml['ActiveServers'], label='With ML', marker='o', color='blue')
plt.plot(baseline['Time'], baseline['ActiveServers'], label='Baseline', marker='x', color='red')
plt.title("Active Servers")
plt.xlabel("Time")
plt.ylabel("Servers")
plt.legend()
plt.grid(True)

plt.tight_layout()
plt.suptitle("ML vs Baseline Comparison", fontsize=16, y=1.05)
plt.show()
plt.tight_layout()
plt.suptitle("ML vs Baseline Comparison", fontsize=16, y=1.05)

# Save plot before showing
plt.savefig("ml_vs_baseline_comparison.png", dpi=300)
plt.show()
