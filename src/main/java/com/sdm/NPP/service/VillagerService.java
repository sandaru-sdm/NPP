package com.sdm.NPP.service;

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

        WriteResult result = docRef.set(villager).get();
        return villager;
    }

    public List<Villager> searchVillagers(String name, String address, String division) throws ExecutionException, InterruptedException {
        Query query = firestore.collection("villagers");

        if (name != null && !name.isEmpty()) {
            query = query.whereEqualTo("name", name);
        }
        if (address != null && !address.isEmpty()) {
            query = query.whereEqualTo("address.line1", address); // Searching by line1
        }
        if (division != null && !division.isEmpty()) {
            query = query.whereEqualTo("address.division", division);
        }

        QuerySnapshot querySnapshot = query.get().get();

        List<Villager> villagers = new ArrayList<>();
        querySnapshot.forEach(document -> villagers.add(document.toObject(Villager.class)));
        return villagers;
    }
}
