package com.example.giaodichnongsan.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.giaodichnongsan.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthRepository {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // ===== ĐĂNG KÝ =====
    public MutableLiveData<String> register(String hoTen, String sdt, String email, String pass) {

        MutableLiveData<String> result = new MutableLiveData<>();
        // result trả về: null = thành công, String = thông báo lỗi

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {

                    String uid = authResult.getUser().getUid();

                    // Tạo user object lưu lên Firestore
                    User user = new User(uid, hoTen, sdt, email);

                    db.collection("users")
                            .document(uid)
                            .set(user)
                            .addOnSuccessListener(v -> result.setValue(null)) // null = ok
                            .addOnFailureListener(e -> result.setValue("Lỗi lưu thông tin: " + e.getMessage()));
                })
                .addOnFailureListener(e -> result.setValue(e.getMessage()));

        return result;
    }

    // ===== ĐĂNG NHẬP =====
    public MutableLiveData<String> login(String email, String pass) {

        MutableLiveData<String> result = new MutableLiveData<>();

        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> result.setValue(null)) // null = ok
                .addOnFailureListener(e -> result.setValue(e.getMessage()));

        return result;
    }

    // ===== LẤY USER HIỆN TẠI =====
    public MutableLiveData<User> getCurrentUserData() {

        MutableLiveData<User> result = new MutableLiveData<>();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser == null) {
            result.setValue(null);
            return result;
        }

        db.collection("users")
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(snapshot -> {
                    User user = snapshot.toObject(User.class);
                    result.setValue(user);
                })
                .addOnFailureListener(e -> result.setValue(null));

        return result;
    }

    // ===== ĐĂNG XUẤT =====
    public void logout() {
        auth.signOut();
    }

    // ===== KIỂM TRA ĐÃ LOGIN CHƯA =====
    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }
}