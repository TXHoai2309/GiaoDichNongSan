package com.example.giaodichnongsan.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import com.example.giaodichnongsan.ui.activity.DangNhap;

public class AuthHelper {

    // Kiểm tra đã đăng nhập chưa (dùng SharedPreferences, sau thay bằng FirebaseAuth)
    public static boolean isLoggedIn(Context context) {
        return context.getSharedPreferences("USER", Context.MODE_PRIVATE)
                .getBoolean("isLoggedIn", false);
    }

    // Nếu chưa login → show dialog mời đăng nhập
    public static void requireLogin(Context context, Runnable onLoggedIn) {
        if (isLoggedIn(context)) {
            onLoggedIn.run();
            return;
        }

        new AlertDialog.Builder(context)
                .setTitle("Yêu cầu đăng nhập")
                .setMessage("Bạn cần đăng nhập để thực hiện chức năng này.")
                .setPositiveButton("Đăng nhập", (d, w) -> {
                    context.startActivity(new Intent(context, DangNhap.class));
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}