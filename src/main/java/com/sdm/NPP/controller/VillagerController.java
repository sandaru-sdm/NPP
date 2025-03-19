package com.sdm.NPP.controller;

import com.sdm.NPP.dto.VillagerRequest;
import com.sdm.NPP.model.Villager;
import com.sdm.NPP.service.VillagerService;
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

    @PostMapping("/save")
    public ResponseEntity<Villager> saveVillager(@RequestBody VillagerRequest villagerRequest) {
        try {
            Villager savedVillager = villagerService.saveVillager(villagerRequest);
            return ResponseEntity.ok(savedVillager);
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
