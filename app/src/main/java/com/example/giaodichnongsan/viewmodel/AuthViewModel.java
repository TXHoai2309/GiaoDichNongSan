package com.example.giaodichnongsan.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.giaodichnongsan.data.repository.AuthRepository;
import com.example.giaodichnongsan.model.User;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository = new AuthRepository();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading()    { return isLoading; }
    public LiveData<String>  getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> getIsSuccess()    { return isSuccess; }
    public LiveData<User>    getCurrentUser()  { return currentUser; }

    // ===== ĐĂNG KÝ =====
    public void register(String hoTen, String sdt, String email, String pass) {
        isLoading.setValue(true);

        repository.register(hoTen, sdt, email, pass).observeForever(error -> {
            isLoading.setValue(false);
            if (error == null) {
                isSuccess.setValue(true);
            } else {
                errorMessage.setValue(error);
            }
        });
    }

    // ===== ĐĂNG NHẬP =====
    public void login(String email, String pass) {
        isLoading.setValue(true);

        repository.login(email, pass).observeForever(error -> {
            isLoading.setValue(false);
            if (error == null) {
                isSuccess.setValue(true);
            } else {
                errorMessage.setValue(error);
            }
        });
    }

    // ===== LẤY THÔNG TIN USER =====
    public void loadCurrentUser() {
        repository.getCurrentUserData().observeForever(user -> currentUser.setValue(user));
    }

    // ===== ĐĂNG XUẤT =====
    public void logout() {
        repository.logout();
    }

    public boolean isLoggedIn() {
        return repository.isLoggedIn();
    }
}