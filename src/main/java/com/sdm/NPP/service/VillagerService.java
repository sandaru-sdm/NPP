package com.sdm.NPP.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.sdm.NPP.dto.VillagerRequest;
import com.sdm.NPP.model.Villager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class VillagerService {

    private final Firestore firestore;

    public VillagerService(Firestore firestore) {
        this.firestore = firestore;
    }

    public Villager saveVillager(VillagerRequest villagerRequest) throws ExecutionException, InterruptedException {
        Villager villager = new Villager(villagerRequest.getName(), villagerRequest.getGender(), villagerRequest.getAddress());

        DocumentReference docRef = firestore.collection("villagers").document();
        villager.setId(docRef.getId());

        docRef.set(villager).get();
        return villager;
    }

    public Villager updateVillager(String id, VillagerRequest villagerRequest) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("villagers").document(id);

        DocumentSnapshot snapshot = docRef.get().get();
        if (!snapshot.exists()) {
            throw new RuntimeException("Villager with ID " + id + " not found.");
        }

        docRef.update(
                "name", villagerRequest.getName(),
                "gender", villagerRequest.getGender(),
                "address", villagerRequest.getAddress()
        ).get();

        return new Villager(villagerRequest.getName(), villagerRequest.getGender(), villagerRequest.getAddress());
    }

    public Villager getVillagerById(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("villagers").document(id);
        DocumentSnapshot snapshot = docRef.get().get();

        if (!snapshot.exists()) {
            throw new RuntimeException("Villager with ID " + id + " not found.");
        }

        return snapshot.toObject(Villager.class);
    }

    public List<Villager> getAllVillagers() throws ExecutionException, InterruptedException {
        CollectionReference villagersCollection = firestore.collection("villagers");
        ApiFuture<QuerySnapshot> future = villagersCollection.get();

        List<Villager> villagers = new ArrayList<>();
        for (DocumentSnapshot document : future.get().getDocuments()) {
            villagers.add(document.toObject(Villager.class));
        }

        return villagers;
    }

    public List<Villager> searchVillagers(String name, String address, String division) throws ExecutionException, InterruptedException {
        CollectionReference collection = firestore.collection("villagers");
        List<Villager> villagers = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            Query query = collection.orderBy("name").startAt(name).endAt(name + "\uf8ff");
            QuerySnapshot querySnapshot = query.get().get();
            querySnapshot.forEach(document -> villagers.add(document.toObject(Villager.class)));
        } else if (address != null && !address.isEmpty()) {
            Query query = collection.orderBy("address.number").startAt(address).endAt(address + "\uf8ff");
            QuerySnapshot querySnapshot = query.get().get();
            querySnapshot.forEach(document -> villagers.add(document.toObject(Villager.class)));
        } else if (division != null && !division.isEmpty()) {
            Query query = collection.orderBy("address.division").startAt(division).endAt(division + "\uf8ff");
            QuerySnapshot querySnapshot = query.get().get();
            querySnapshot.forEach(document -> villagers.add(document.toObject(Villager.class)));
        }

        return villagers;
    }

    public int getVillagersCount() throws ExecutionException, InterruptedException {
        CollectionReference villagersCollection = firestore.collection("villagers");
        ApiFuture<QuerySnapshot> future = villagersCollection.get();
        return future.get().size();
    }

    public int getNorthDivisionVillagersCount() throws ExecutionException, InterruptedException {
        CollectionReference villagersCollection = firestore.collection("villagers");

        ApiFuture<QuerySnapshot> query = villagersCollection
                .whereEqualTo("address.division", "North")
                .get();

        QuerySnapshot querySnapshot = query.get();

        return querySnapshot.size();
    }

    public int getSouthDivisionVillagersCount() throws ExecutionException, InterruptedException {
        CollectionReference villagersCollection = firestore.collection("villagers");

        ApiFuture<QuerySnapshot> query = villagersCollection
                .whereEqualTo("address.division", "South")
                .get();

        QuerySnapshot querySnapshot = query.get();

        return querySnapshot.size();
    }
}