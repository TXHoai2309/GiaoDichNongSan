package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.AuthRepository;
import com.example.giaodichnongsan.model.User;

public class AuthViewModel extends ViewModel {

    private AuthRepository repository = new AuthRepository();

    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public LiveData<User> getUser() {
        return userLiveData;
    }

    // ===== ĐĂNG KÝ =====
    public void register(String hoTen, String sdt, String email, String pass) {
        repository.register(hoTen, sdt, email, pass)
                .observeForever(user -> userLiveData.setValue(user));
    }

    // ===== ĐĂNG NHẬP =====
    public void login(String email, String pass) {
        repository.login(email, pass)
                .observeForever(user -> userLiveData.setValue(user));
    }

    public User getCurrentUser() {
        return repository.getCurrentUser();
    }

    public void logout() {
        repository.logout();
        userLiveData.setValue(null);
    }
}