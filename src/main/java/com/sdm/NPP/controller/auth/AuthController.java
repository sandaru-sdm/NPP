package com.sdm.NPP.controller.auth;

import com.sdm.NPP.dto.LoginRequest;
import com.sdm.NPP.dto.RegisterRequest;
import com.sdm.NPP.dto.UpdateRoleRequest;
import com.sdm.NPP.dto.UpdateStatusRequest;
import com.sdm.NPP.model.User;
import com.sdm.NPP.service.UserService;
import com.sdm.NPP.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() throws ExecutionException, InterruptedException {
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest)
            throws ExecutionException, InterruptedException {
        String response = userService.registerUser(
                registerRequest.getAdminUsername(),
                registerRequest.getName(),
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getMobileNumber(),
                registerRequest.getRole()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest)
            throws ExecutionException, InterruptedException {
        System.out.println("Login attempt: " + loginRequest.getUsername());
        String authResponse = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        System.out.println("Auth response: " + authResponse);

        if (authResponse.startsWith("Authenticated")) {
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", loginRequest.getUsername());
            response.put("role", authResponse.replace("Authenticated as: ", ""));
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", authResponse));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) throws ExecutionException, InterruptedException {
        User user = userService.getUser(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PutMapping("/role")
    public ResponseEntity<String> updateRole(@RequestBody UpdateRoleRequest updateRoleRequest) throws ExecutionException, InterruptedException {
        String response = userService.updateUserRole(
                updateRoleRequest.getAdminUsername(),
                updateRoleRequest.getUsername(),
                updateRoleRequest.getNewRole()
        );
        if (response.contains("updated")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(response);
        }
    }

    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestBody UpdateStatusRequest updateStatusRequest) throws ExecutionException, InterruptedException {
        String response = userService.updateUserStatus(
                updateStatusRequest.getAdminUsername(),
                updateStatusRequest.getUsername(),
                updateStatusRequest.getNewStatus()
        );
        if (response.contains("updated")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(response);
        }
    }

}
