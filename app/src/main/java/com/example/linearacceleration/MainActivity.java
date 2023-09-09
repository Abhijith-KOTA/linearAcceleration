package com.example.linearacceleration;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private TextView xAccelerationTextView;
    private TextView yAccelerationTextView;
    private TextView zAccelerationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xAccelerationTextView = findViewById(R.id.xAcceleration);
        yAccelerationTextView = findViewById(R.id.yAcceleration);
        zAccelerationTextView = findViewById(R.id.zAcceleration);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Ignore values close to the gravitational acceleration
            double gravitationalThreshold = 0.2; // Adjust this threshold as needed
            if (Math.abs(x) < gravitationalThreshold && Math.abs(y) < gravitationalThreshold && Math.abs(z - 9.81) < gravitationalThreshold) {
                x = 0;
                y = 0;
                z = 0;
            }

            xAccelerationTextView.setText("X Acceleration: " + x);
            yAccelerationTextView.setText("Y Acceleration: " + y);
            zAccelerationTextView.setText("Z Acceleration: " + z);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }
}
