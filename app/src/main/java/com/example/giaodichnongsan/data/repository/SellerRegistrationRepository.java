package com.example.giaodichnongsan.data.repository;

import com.example.giaodichnongsan.model.SellerRegistrationRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void getPendingRequests(OnListListener listener) {
        db.collection("seller_requests")
                .whereEqualTo("trangThai", "CHO_DUYET")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<SellerRegistrationRequest> list = new ArrayList<>();
                    queryDocumentSnapshots.getDocuments().sort((a, b) ->
                            a.getId().compareToIgnoreCase(b.getId())
                    );

                    for (com.google.firebase.firestore.DocumentSnapshot doc : queryDocumentSnapshots) {
                        SellerRegistrationRequest request = doc.toObject(SellerRegistrationRequest.class);
                        if (request != null) {
                            request.setId(doc.getId());
                            list.add(request);
                        }
                    }

                    if (listener != null) listener.onSuccess(list);
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e.getMessage());
                });
    }

    public void approveRequest(SellerRegistrationRequest request, OnSubmitListener listener) {
        if (request == null || request.getId() == null) {
            if (listener != null) listener.onFailure("Không tìm thấy yêu cầu");
            return;
        }

        db.collection("seller_requests")
                .document(request.getId())
                .update("trangThai", "DA_DUYET")
                .addOnSuccessListener(unused -> updateUserSellerStatus(request, listener))
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e.getMessage());
                });
    }

    public void rejectRequest(String requestId, OnSubmitListener listener) {
        if (requestId == null || requestId.isEmpty()) {
            if (listener != null) listener.onFailure("Không tìm thấy yêu cầu");
            return;
        }

        db.collection("seller_requests")
                .document(requestId)
                .update("trangThai", "TU_CHOI")
                .addOnSuccessListener(unused -> {
                    if (listener != null) listener.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e.getMessage());
                });
    }

    private void updateUserSellerStatus(SellerRegistrationRequest request, OnSubmitListener listener) {
        Map<String, Object> data = new HashMap<>();
        data.put("seller", true);
        data.put("isSeller", true);
        data.put("shopId", request.getId());

        if (request.getUserId() != null && !request.getUserId().isEmpty()) {
            db.collection("users")
                    .document(request.getUserId())
                    .update(data)
                    .addOnSuccessListener(unused -> {
                        if (listener != null) listener.onSuccess();
                    })
                    .addOnFailureListener(e -> {
                        if (listener != null) listener.onFailure(e.getMessage());
                    });
            return;
        }

        db.collection("users")
                .whereEqualTo("email", request.getEmail())
                .limit(1)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.isEmpty()) {
                        if (listener != null) listener.onSuccess();
                        return;
                    }

                    snapshot.getDocuments().get(0).getReference()
                            .update(data)
                            .addOnSuccessListener(unused -> {
                                if (listener != null) listener.onSuccess();
                            })
                            .addOnFailureListener(e -> {
                                if (listener != null) listener.onFailure(e.getMessage());
                            });
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e.getMessage());
                });
    }

    public interface OnSubmitListener {
        void onSuccess();
        void onFailure(String error);
    }

    public interface OnListListener {
        void onSuccess(ArrayList<SellerRegistrationRequest> list);
        void onFailure(String error);
    }
}
