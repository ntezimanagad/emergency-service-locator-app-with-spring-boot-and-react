package com.emergence.locator.app.emergence.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emergence.locator.app.emergence.dto.UserDTO;
import com.emergence.locator.app.emergence.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Step 1: Request OTP for registration
    @PostMapping("/register/request-otp")
    public ResponseEntity<?> requestRegistrationOtp(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
            return ResponseEntity.ok("OTP sent for registration to email: " + userDTO.getEmail());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Step 2: Validate OTP for registration
    @PostMapping("/register/validate-otp")
    public ResponseEntity<?> validateRegistrationOtp(@RequestBody UserDTO userDTO) {
        try {
            userService.validateOtp(userDTO);
            return ResponseEntity.ok("OTP validated successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Step 3: Create new user after OTP validation
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUser = userService.createNew(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Step 1: Login request (generate OTP)
    @PostMapping("/login/request-otp")
    public ResponseEntity<?> requestLoginOtp(@RequestBody UserDTO userDTO) {
        try {
            userService.loginUser(userDTO);
            return ResponseEntity.ok("OTP sent for login to email: " + userDTO.getEmail());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Step 2: Validate login OTP and return JWT token
    @PostMapping("/login/validate-otp")
    public ResponseEntity<?> validateLoginOtp(@RequestBody UserDTO userDTO) {
        try {
            String token = userService.validateLogin(userDTO);
            return ResponseEntity.ok(token);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    // Step 1: Request OTP for password reset
    @PostMapping("/password/reset/request-otp")
    public ResponseEntity<?> requestPasswordResetOtp(@RequestBody UserDTO userDTO) {
        try {
            userService.resetPassword(userDTO);
            return ResponseEntity.ok("OTP sent for password reset to email: " + userDTO.getEmail());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Step 2: Validate OTP for password reset
    @PostMapping("/password/reset/validate-otp")
    public ResponseEntity<?> validatePasswordResetOtp(@RequestBody UserDTO userDTO) {
        try {
            userService.validatePasswordReset(userDTO);
            return ResponseEntity.ok("OTP validated successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Step 3: Update password after OTP validation
    @PostMapping("/password/update")
    public ResponseEntity<?> updatePassword(@RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updatePassword(userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Logout by blacklisting the token
    // @PostMapping("/logout")
    // public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
    //     try {
    //         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    //             return ResponseEntity.badRequest().body("Invalid Authorization header.");
    //         }
    //         String token = authHeader.substring(7);
    //         userService.logout(token);
    //         return ResponseEntity.ok("Logged out successfully.");
    //     } catch (RuntimeException ex) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    //     }
    // }
    
}
