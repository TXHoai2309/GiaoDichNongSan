package com.example.giaodichnongsan.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
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
    private ShakeDetector shakeDetector;

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
            }
            else if (item.getItemId() == R.id.nav_giohang) {
                loadFragment(new GioHangFragment());
                return true;
            }
            else if (item.getItemId() == R.id.nav_order) {
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
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            if (accelerometer != null) {
                shakeDetector = new ShakeDetector(() -> {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Đã phát hiện rung!", Toast.LENGTH_SHORT).show();
                        openSupportScreen();
                    });
                });
            }if (accelerometer == null) {
                Toast.makeText(this, "Không có cảm biến!", Toast.LENGTH_LONG).show();
            }

        }
    }
    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
    private void openSupportScreen() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

        if (currentFragment instanceof HelpCenterFragment) return;

        Fragment fragment = new HelpCenterFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateShakeState(); // 🔥 gọi lại thôi
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (sensorManager != null) {
            sensorManager.unregisterListener(shakeDetector);
        }
    }
    public void updateShakeState() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isShakeEnabled = prefs.getBoolean("shake_enabled", true);

        if (sensorManager != null && accelerometer != null && shakeDetector != null) {

            if (isShakeEnabled) {
                sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
            } else {
                sensorManager.unregisterListener(shakeDetector);
            }
        }
    }

}