package com.example.giaodichnongsan;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.*;

public class DataSeeder {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void seedAll() {
        seedUsers();
        seedSellerRequests();
        seedStores();
        seedCategories();
        seedProducts();
        seedCart();
        seedOrders();
        seedReviews();
        seedReports();
        seedNews();
    }

    public void seedUsers() {
        db.collection("users").document("user0002").set(user(
                "Trần Thị Lan",
                "lan@gmail.com",
                "0988111222",
                "Hà Nội",
                "Khách hàng",
                false
        ));

        db.collection("users").document("user0003").set(user(
                "Lê Văn Nam",
                "nam@gmail.com",
                "0977222333",
                "Đà Nẵng",
                "Khách hàng",
                false
        ));

        db.collection("users").document("user0004").set(user(
                "Phạm Thu Hà",
                "ha@gmail.com",
                "0966333444",
                "Hải Phòng",
                "Khách hàng",
                true
        ));

        db.collection("users").document("user0005").set(user(
                "Trần Hồng Tuần Phong",
                "thtphong@gmail.com",
                "0387334552",
                "Đà Lạt",
                "Khách hàng",
                true
        ));
    }

    private Map<String, Object> user(String fullName, String email, String phone,
                                     String address, String role, boolean isSeller) {
        Map<String, Object> map = new HashMap<>();
        map.put("full_name", fullName);
        map.put("email", email);
        map.put("phone", phone);
        map.put("address", address);
        map.put("role", role);
        map.put("is_seller", isSeller);
        map.put("avatar_url", "url");
        return map;
    }

    public void seedSellerRequests() {
        Map<String, Object> req0002 = new HashMap<>();
        req0002.put("user_id", "user0004");
        req0002.put("full_name", "Phạm Thu Hà");
        req0002.put("phone", "0966333444");
        req0002.put("email", "ha@gmail.com");
        req0002.put("cccd", "012345678901");
        req0002.put("address", "Hải Phòng");
        req0002.put("store_name", "Nông sản sạch Hà Farm");
        req0002.put("store_description", "Rau sạch");
        req0002.put("production_address", "Hải Phòng");
        req0002.put("product_type", "Rau");
        req0002.put("product_origin", "Hải Phòng");
        req0002.put("food_safety_certificate_img", Arrays.asList("url"));
        req0002.put("vietgap_certificate_img", Arrays.asList("url"));
        req0002.put("bank_account_number", "123456789");
        req0002.put("bank_account_name", "PHẠM THU HÀ");
        req0002.put("request_status", "Chấp nhận");

        Map<String, Object> req0003 = new HashMap<>();
        req0003.put("user_id", "user0005");
        req0003.put("full_name", "Trần Hồng Tuần Phong");
        req0003.put("phone", "0387334552");
        req0003.put("email", "thtphong@gmail.com");
        req0003.put("cccd", "037484000888");
        req0003.put("address", "Đà Lạt");
        req0003.put("store_name", "Rau củ xanh xanh");
        req0003.put("store_description", "Rau củ tươi xanh từ Đà Lạt, vô cùng đảm bảo");
        req0003.put("production_address", "Đà Lạt");
        req0003.put("product_type", "Rau, củ, hoa quả");
        req0003.put("product_origin", "Đà Lạt");
        req0003.put("food_safety_certificate_img", Arrays.asList("ảnh 1", "ảnh 2"));
        req0003.put("vietgap_certificate_img", Arrays.asList("ảnh 1", "ảnh 2"));
        req0003.put("bank_account_number", "121234345");
        req0003.put("bank_account_name", "Phung Thanh Do");
        req0003.put("request_status", "Chấp nhận");

        db.collection("seller_requests").document("req0002").set(req0002);
        db.collection("seller_requests").document("req0003").set(req0003);
    }

    public void seedStores() {
        Map<String, Object> store0002 = new HashMap<>();
        store0002.put("owner_id", "user0004");
        store0002.put("store_name", "Nông sản sạch Hà Farm");
        store0002.put("address", "Hải Phòng");
        store0002.put("phone", "0966333444");
        store0002.put("description", "Rau sạch");
        store0002.put("image_url", "url");
        store0002.put("bank_name", "Vietcombank");
        store0002.put("bank_account_number", "123456789");
        store0002.put("bank_account_name", "PHAM THU HA");
        store0002.put("qr_img_url", "url");

        Map<String, Object> store0003 = new HashMap<>();
        store0003.put("owner_id", "user0005");
        store0003.put("store_name", "Rau củ xanh xanh");
        store0003.put("address", "Đà Lạt");
        store0003.put("phone", "03546242917");
        store0003.put("description", "Chuyên cung cấp rau xanh, quả sạch ,đảm bảo chất lượng!");
        store0003.put("image_url", "url");
        store0003.put("bank_name", "Vietcombank");
        store0003.put("bank_account_number", "121234345");
        store0003.put("bank_account_name", "TRAN HONG TUAN PHONG");
        store0003.put("qr_img_url", "url");

        db.collection("stores").document("store0002").set(store0002);
        db.collection("stores").document("store0003").set(store0003);
    }

    public void seedCategories() {
        db.collection("categories").document("cate0002")
                .set(single("category_name", "Củ"));
        db.collection("categories").document("cate0003")
                .set(single("category_name", "Hoa quả"));
    }

    public void seedProducts() {
        db.collection("products").document("prod0002").set(product(
                "store0002", "cate0002", "Rau cải xanh",
                "Rau sạch", 25000L, 40L, "kg", "Hải Phòng"
        ));

        db.collection("products").document("prod0003").set(product(
                "store0002", "cate0002", "Cà rốt",
                "Cà rốt sạch", 20000L, 50L, "kg", "Hải Phòng"
        ));

        db.collection("products").document("prod0004").set(product(
                "store0003", "cate0002", "Rau xà lách",
                "Rau Đà Lạt", 25000L, 40L, "kg", "Đà Lạt"
        ));

        db.collection("products").document("prod0005").set(product(
                "store0003", "cate0002", "Cà rốt",
                "Cà rốt Đà Lạt", 20000L, 50L, "kg", "Đà Lạt"
        ));

        db.collection("products").document("prod0006").set(product(
                "store0003", "cate0003", "Táo đỏ",
                "Táo tươi sạch từ Đà Lạt", 45000L, 25L, "qua/cu", "Đà Lạt"
        ));

        db.collection("products").document("prod0007").set(product(
                "store0003", "cate0003", "Chuối già",
                "Chuối chín tự nhiên", 12000L, 80L, "qua/cu", "Đà Lạt"
        ));
    }

    private Map<String, Object> product(String storeId, String categoryId, String productName,
                                        String description, long price, long stock,
                                        String unit, String origin) {
        Map<String, Object> map = new HashMap<>();
        map.put("store_id", storeId);
        map.put("category_id", categoryId);
        map.put("product_name", productName);
        map.put("description", description);
        map.put("price", price);
        map.put("stock", stock);
        map.put("unit", unit);
        map.put("origin", origin);
        map.put("image_url", "url");
        return map;
    }

    public void seedCart() {
        Map<String, Object> cart0002 = new HashMap<>();
        List<Map<String, Object>> items0002 = new ArrayList<>();
        items0002.add(cartItem("prod0002", "Rau cải", 25000L, 2L, "store0002", true));
        cart0002.put("items", items0002);

        Map<String, Object> cart0005 = new HashMap<>();
        List<Map<String, Object>> items0005 = new ArrayList<>();
        items0005.add(cartItem("prod0004", "Rau cải xanh", 25000L, 1L, "store0003", true));
        cart0005.put("items", items0005);

        db.collection("cart").document("user0002").set(cart0002);
        db.collection("cart").document("user0005").set(cart0005);
    }

    private Map<String, Object> cartItem(String productId, String productName, long price,
                                         long quantity, String storeId, boolean selected) {
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);
        map.put("product_name", productName);
        map.put("price", price);
        map.put("quantity", quantity);
        map.put("store_id", storeId);
        map.put("selected", selected);
        return map;
    }

    public void seedOrders() {
        Map<String, Object> order0002 = new HashMap<>();
        order0002.put("user_id", "user0002");
        order0002.put("store_id", "store0002");
        order0002.put("shipping_name", "Trần Thị Lan");
        order0002.put("phone", "0988111222");
        order0002.put("shipping_address", "Hà Nội");
        order0002.put("shipping_fee", 20000L);
        order0002.put("payment_method", "COD");
        order0002.put("payment_status", "Chưa trả");
        order0002.put("qr_image", "");
        order0002.put("order_status", "Chờ phản hồi");
        order0002.put("cancel_reason", "");
        order0002.put("cancelled_by", "");
        order0002.put("total_amount", 70000L);
        order0002.put("items", Collections.singletonList(orderItem(
                "prod0002", "Rau cải", 25000L, 2L, 50000L
        )));

        Map<String, Object> order0003 = new HashMap<>();
        order0003.put("user_id", "user0005");
        order0003.put("store_id", "store0003");
        order0003.put("shipping_name", "Trần Hồng Tuần Phong");
        order0003.put("phone", "0387334552");
        order0003.put("shipping_address", "Đà Lạt");
        order0003.put("shipping_fee", 20000L);
        order0003.put("payment_method", "QR");
        order0003.put("payment_status", "Đã trả");
        order0003.put("qr_image", "url");
        order0003.put("order_status", "Đang giao");
        order0003.put("cancel_reason", "");
        order0003.put("cancelled_by", "");
        order0003.put("total_amount", 70000L);
        order0003.put("items", Collections.singletonList(orderItem(
                "prod0004", "Rau cải xanh", 25000L, 2L, 50000L
        )));

        db.collection("orders").document("order0002").set(order0002);
        db.collection("orders").document("order0003").set(order0003);
    }

    private Map<String, Object> orderItem(String productId, String productName,
                                          long price, long quantity, long subTotal) {
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);
        map.put("product_name", productName);
        map.put("price", price);
        map.put("quantity", quantity);
        map.put("sub_total", subTotal);
        return map;
    }

    public void seedReviews() {
        Map<String, Object> revi0002 = new HashMap<>();
        revi0002.put("user_id", "user0002");
        revi0002.put("produc_id", "prod0002");
        revi0002.put("rating", 5L);
        revi0002.put("comment", "Rất tươi");

        Map<String, Object> revi0003 = new HashMap<>();
        revi0003.put("user_id", "user0005");
        revi0003.put("produc_id", "prod0004");
        revi0003.put("rating", 5L);
        revi0003.put("comment", "Rất ngon");

        db.collection("reviews").document("revi0002").set(revi0002);
        db.collection("reviews").document("revi0003").set(revi0003);
    }

    public void seedReports() {
        Map<String, Object> repo0002 = new HashMap<>();
        repo0002.put("reporter_id", "user0002");
        repo0002.put("target_type", "Sản phẩm");
        repo0002.put("target_id", "prod0003");
        repo0002.put("reason", "Giá cao");
        repo0002.put("description", "Không hợp lý");
        repo0002.put("evidence_url", "url");

        db.collection("reports").document("repo0002").set(repo0002);
    }

    public void seedNews() {
        Map<String, Object> news0002 = new HashMap<>();
        news0002.put("title", "Giá rau tăng");
        news0002.put("content", "Thị trường biến động");
        news0002.put("source", "VTV");
        news0002.put("published_at", "2026-04-10");
        news0002.put("news_image_url", "url");

        db.collection("news").document("news0002").set(news0002);
    }

    private Map<String, Object> single(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}