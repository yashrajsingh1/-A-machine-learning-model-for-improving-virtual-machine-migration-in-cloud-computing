from flask import Flask, request, jsonify
import joblib
import numpy as np
import os

app = Flask(__name__)

# Load models
model_dir = os.path.join(os.path.dirname(__file__), 'models')
lgbm_cpu = joblib.load(os.path.join(model_dir, 'lgbm_cpu_usage.joblib'))
lgbm_mem = joblib.load(os.path.join(model_dir, 'lgbm_memory_usage.joblib'))
lgbm_net = joblib.load(os.path.join(model_dir, 'lgbm_network_traffic.joblib'))

@app.route('/')
def home():
    return "VM Resource Prediction API is running!"

@app.route('/predict', methods=['POST'])
def predict():
    try:
        input_data = request.json  # JSON data as input
        features = np.array(input_data['features']).reshape(1, -1)

        pred_cpu = lgbm_cpu.predict(features)[0]
        pred_mem = lgbm_mem.predict(features)[0]
        pred_net = lgbm_net.predict(features)[0]

        return jsonify({
            'cpu_usage': float(pred_cpu),
            'memory_usage': float(pred_mem),
            'network_traffic': float(pred_net)
        })
    except Exception as e:
        return jsonify({'error': str(e)})

if __name__ == '__main__':
    app.run(debug=True)
