package com.sdm.NPP.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.sdm.NPP.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    private final Firestore firestore;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(Firestore firestore) {
        this.firestore = firestore;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String registerUser(String adminUsername, String name, String username, String password, String mobileNumber, String role)
            throws ExecutionException, InterruptedException {
        DocumentReference adminRef = firestore.collection("users").document(adminUsername);
        DocumentSnapshot adminDoc = adminRef.get().get();

        if (!adminDoc.exists() || !"ADMIN".equals(adminDoc.getString("role"))) {
            return "Unauthorized! Only ADMIN can add new users.";
        }

        DocumentReference userRef = firestore.collection("users").document(username);
        if (userRef.get().get().exists()) {
            return "User already exists!";
        }

        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(name, username, hashedPassword, mobileNumber, role, true);

        WriteResult result = userRef.set(user).get();
        return "User added at: " + result.getUpdateTime();
    }

    public String authenticateUser(String username, String password) throws ExecutionException, InterruptedException {
        System.out.println("Authenticating user: " + username);

        DocumentReference docRef = firestore.collection("users").document(username);
        DocumentSnapshot document = docRef.get().get();

        if (!document.exists()) {
            System.out.println("User not found in Firestore");
            return "Invalid credentials!";
        }

        User user = document.toObject(User.class);
        System.out.println("User found: " + user.getUsername());

        if (!user.getIsActive()) {
            return "User is inactive!";
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return "Authenticated as: " + user.getRole();
        }

        return "Invalid password!";
    }


    public String updateUserRole(String adminUsername, String username, String newRole) throws ExecutionException, InterruptedException {
        DocumentReference adminRef = firestore.collection("users").document(adminUsername);
        DocumentSnapshot adminDoc = adminRef.get().get();

        if (!adminDoc.exists() || !"ADMIN".equals(adminDoc.getString("role"))) {
            return "Unauthorized! Only ADMIN can change roles.";
        }

        DocumentReference userRef = firestore.collection("users").document(username);
        userRef.update("role", newRole).get();
        return "User role updated to: " + newRole;
    }

    public String updateUserStatus(String adminUsername, String username, Boolean newStatus) throws ExecutionException, InterruptedException {
        DocumentReference adminRef = firestore.collection("users").document(adminUsername);
        DocumentSnapshot adminDoc = adminRef.get().get();

        if (!adminDoc.exists() || !"ADMIN".equals(adminDoc.getString("role"))) {
            return "Unauthorized! Only ADMIN can change status.";
        }

        DocumentReference userRef = firestore.collection("users").document(username);
        userRef.update("isActive", newStatus).get();
        return "User status updated to: " + (newStatus ? "Active" : "Inactive");
    }

    public User getUser(String username) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users").document(username);
        DocumentSnapshot document = docRef.get().get();

        if (document.exists()) {
            return document.toObject(User.class);
        }
        return null;
    }

    public void createDefaultAdmin() throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = firestore.collection("users")
                .whereEqualTo("role", "ADMIN")
                .limit(1)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (documents.isEmpty()) {

            String hashedPassword = passwordEncoder.encode("dilshan2000");

            User adminUser = new User(
                    "Sandaru Gunathilake",
                    "maduhansadilshan@gmail.com",
                    hashedPassword,
                    "0701794934",
                    "ADMIN",
                    true
            );

            firestore.collection("users").document("maduhansadilshan@gmail.com").set(adminUser).get();
            System.out.println("Default Admin Created: Username: maduhansadilshan@gmail.com, Password: dilshan2000");
        } else {
            System.out.println("Admin already exists, skipping creation.");
        }
    }

    public List<User> getAllUsers() throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection("users");
        ApiFuture<QuerySnapshot> future = usersCollection.get();
        List<User> users = new ArrayList<>();

        for (DocumentSnapshot document : future.get().getDocuments()) {
            if (document.exists()) {
                users.add(document.toObject(User.class));
            }
        }
        return users;
    }

    public int getUsersCount() throws ExecutionException, InterruptedException{
        CollectionReference usersCollection = firestore.collection("users");
        ApiFuture<QuerySnapshot> future = usersCollection.get();
        return future.get().size();
    }
}
