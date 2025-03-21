package com.sdm.NPP.controller;

import com.sdm.NPP.dto.VillagerRequest;
import com.sdm.NPP.model.Villager;
import com.sdm.NPP.service.VillagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/villagers")
public class VillagerController {

    private final VillagerService villagerService;

    public VillagerController(VillagerService villagerService) {
        this.villagerService = villagerService;
    }

    @GetMapping
    public ResponseEntity<List<Villager>> getAllVillagers() {
        try {
            List<Villager> villagers = villagerService.getAllVillagers();
            return new ResponseEntity<>(villagers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Villager> getVillagerById(@PathVariable String id) {
        try {
            Villager villager = villagerService.getVillagerById(id);
            return ResponseEntity.ok(villager);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Villager> saveVillager(@RequestBody VillagerRequest villagerRequest) {
        try {
            Villager savedVillager = villagerService.saveVillager(villagerRequest);
            return ResponseEntity.ok(savedVillager);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Villager> updateVillager(@PathVariable String id, @RequestBody VillagerRequest villagerRequest) {
        try {
            Villager updatedVillager = villagerService.updateVillager(id, villagerRequest);
            return ResponseEntity.ok(updatedVillager);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Villager>> searchVillagers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String division) {
        try {
            List<Villager> result = villagerService.searchVillagers(name, address, division);
            return ResponseEntity.ok(result);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
