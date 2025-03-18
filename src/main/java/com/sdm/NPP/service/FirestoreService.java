package com.sdm.NPP.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.sdm.NPP.model.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {
    private final Firestore firestore;

    public FirestoreService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String addUser(String userId, String name, String email) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users").document(userId);
        WriteResult result = docRef.set(new User(name, email)).get();
        return "User added at: " + result.getUpdateTime();
    }

    public User getUser(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users").document(userId);
        DocumentSnapshot document = docRef.get().get();

        if (document.exists()) {
            return document.toObject(User.class);
        } else {
            return null;
        }
    }
}

