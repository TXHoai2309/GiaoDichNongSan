package com.example.giaodichnongsan.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.giaodichnongsan.model.User;

public class AuthRepository {

    private static User currentUser = null;

    // ===== ĐĂNG KÝ =====
    public MutableLiveData<User> register(String hoTen, String sdt, String email, String pass) {

        MutableLiveData<User> result = new MutableLiveData<>();

        // 🔥 FAKE (sau thay Firebase)
        User user = new User("UID123", hoTen, sdt, email);
        currentUser = user;

        result.setValue(user);
        return result;
    }

    // ===== ĐĂNG NHẬP =====
    public MutableLiveData<User> login(String email, String pass) {

        MutableLiveData<User> result = new MutableLiveData<>();

        // 🔥 FAKE
        if (email.equals("test@gmail.com") && pass.equals("123456")) {
            User user = new User("UID123", "Test User", "0123456789", email);
            currentUser = user;
            result.setValue(user);
        } else {
            result.setValue(null);
        }

        return result;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}