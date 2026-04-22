package com.example.giaodichnongsan.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.giaodichnongsan.ui.fragment.DonHangFragment;
import com.example.giaodichnongsan.ui.fragment.GioHangFragment;
import com.example.giaodichnongsan.R;
import com.example.giaodichnongsan.ui.fragment.HelpCenterFragment;
import com.example.giaodichnongsan.ui.fragment.TaiKhoanFragment;
import com.example.giaodichnongsan.ui.fragment.TrangChuFragment;
import com.example.giaodichnongsan.utils.ShakeDetector;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor lightSensor;
    private ShakeDetector shakeDetector;
    private SensorEventListener lightListener;
    private static final boolean USE_FAKE_SENSOR = false; // 🔥 bật/tắt ở đây // do máy tôi k có sensor cảm biến nên phải fake, ae nào máy có sensor thì để false, k c thì để true
    private android.os.Handler fakeHandler = new android.os.Handler();
    private Runnable fakeSensorRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loadFragment(new TrangChuFragment());

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                loadFragment(new TrangChuFragment());
                return true;
            } else if (item.getItemId() == R.id.nav_account) {
                loadFragment(new TaiKhoanFragment());
                return true;
            } else if (item.getItemId() == R.id.nav_giohang) {
                loadFragment(new GioHangFragment());
                return true;
            } else if (item.getItemId() == R.id.nav_order) {
                loadFragment(new DonHangFragment());
                return true;
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frameLayout), (v, insets) -> {
            int top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(0, top, 0, 0);
            return insets;
        });

        // 🔥 SENSOR INIT
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {

            // LIGHT SENSOR
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if (lightSensor == null) {
                Toast.makeText(this, "Không có cảm biến ánh sáng!", Toast.LENGTH_LONG).show();
            }

            // SHAKE SENSOR
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                shakeDetector = new ShakeDetector(() -> {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Đã phát hiện rung!", Toast.LENGTH_SHORT).show();
                        openSupportScreen();
                    });
                });
            } else {
                Toast.makeText(this, "Không có cảm biến rung!", Toast.LENGTH_LONG).show();
            }
        }

        // LIGHT LISTENER
        lightListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float lux = event.values[0];
                runOnUiThread(() -> adjustBrightness(lux));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sm != null) {
            java.util.List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);

            for (Sensor s : sensors) {
                android.util.Log.d("SENSOR_LIST", s.getName());
            }
        }

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void openSupportScreen() {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (current instanceof HelpCenterFragment) return;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, new HelpCenterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSensorsState();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (sensorManager != null) {
            sensorManager.unregisterListener(shakeDetector);
            sensorManager.unregisterListener(lightListener);
        }
        stopFakeLightSensor(); // 🔥 thêm dòng này
    }

    public void updateSensorsState() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);

        boolean isShakeEnabled = prefs.getBoolean("shake_enabled", true);
        boolean isLightEnabled = prefs.getBoolean("light_enabled", true);

        // SHAKE
        if (sensorManager != null && accelerometer != null && shakeDetector != null) {

            sensorManager.unregisterListener(shakeDetector); // 🔥 luôn reset trước

            if (isShakeEnabled) {
                sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
            }
        }
//        // LIGHT - real
//        if (sensorManager != null && lightSensor != null && lightListener != null) {
//            sensorManager.unregisterListener(lightListener); // 🔥 QUAN TRỌNG
//            if (isLightEnabled) {
//                sensorManager.registerListener(lightListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
//            }
//        }
        // Fake
        if (USE_FAKE_SENSOR) {
            stopFakeLightSensor(); // reset trước
            startFakeLightSensor();
        } else {

            if (sensorManager != null && lightSensor != null && lightListener != null) {

                sensorManager.unregisterListener(lightListener);

                if (isLightEnabled) {
                    sensorManager.registerListener(lightListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
                }
            }
        }
    }
    private void adjustBrightness(float lux) {
        float brightness;

        if (lux < 50) brightness = 0.1f;
        else if (lux < 200) brightness = 0.3f;
        else if (lux < 1000) brightness = 0.6f;
        else brightness = 1.0f;

        android.view.WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = brightness;
        getWindow().setAttributes(params);
    }
    private void startFakeLightSensor() {

        fakeSensorRunnable = new Runnable() {
            float lux = 0;
            boolean increasing = true;

            @Override
            public void run() {

                // tạo giá trị giả
                if (increasing) {
                    lux += 100;
                    if (lux >= 1000) increasing = false;
                } else {
                    lux -= 100;
                    if (lux <= 0) increasing = true;
                }

                // gọi giống sensor thật
                adjustBrightness(lux);

                android.util.Log.d("FAKE_SENSOR", "Lux: " + lux);

                fakeHandler.postDelayed(this, 1000); // mỗi 1s
            }
        };

        fakeHandler.post(fakeSensorRunnable);
    }
    private void stopFakeLightSensor() {
        if (fakeHandler != null && fakeSensorRunnable != null) {
            fakeHandler.removeCallbacks(fakeSensorRunnable);
        }
    }
}
