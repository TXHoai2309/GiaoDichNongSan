package com.example.giaodichnongsan.utils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class ShakeDetector implements SensorEventListener {

    public interface OnShakeListener {
        void onShake();
    }

    private static final float SHAKE_THRESHOLD = 15.0f;
    private static final int SHAKE_COUNT_REQUIRED = 3; // cần lắc 3 lần
    private static final long SHAKE_TIME_WINDOW = 1500; // trong 1.5 giây

    private int shakeCount = 0;
    private long firstShakeTime = 0;

    private OnShakeListener listener;

    public ShakeDetector(OnShakeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gForce = (float) Math.sqrt(x * x + y * y + z * z);

        if (gForce > SHAKE_THRESHOLD) {

            long now = System.currentTimeMillis();

            if (firstShakeTime == 0) {
                firstShakeTime = now;
            }

            shakeCount++;

            // Nếu quá thời gian thì reset
            if (now - firstShakeTime > SHAKE_TIME_WINDOW) {
                shakeCount = 1;
                firstShakeTime = now;
            }

            // Đủ số lần lắc → trigger
            if (shakeCount >= SHAKE_COUNT_REQUIRED) {
                shakeCount = 0;
                firstShakeTime = 0;

                if (listener != null) {
                    listener.onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}