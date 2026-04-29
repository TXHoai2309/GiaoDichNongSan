package com.example.giaodichnongsan.data.repository;

import com.example.giaodichnongsan.model.SellerRegistrationRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class SellerRegistrationRepository {

    private final FirebaseFirestore db;

    public SellerRegistrationRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void submitSellerRequest(SellerRegistrationRequest request,
                                    OnSubmitListener listener) {

        db.collection("seller_requests")
                .add(request)
                .addOnSuccessListener(documentReference -> {
                    if (listener != null) {
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) {
                        listener.onFailure(e.getMessage());
                    }
                });
    }

    public interface OnSubmitListener {
        void onSuccess();
        void onFailure(String error);
    }
}