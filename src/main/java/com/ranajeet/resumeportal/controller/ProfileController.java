package com.ranajeet.resumeportal.controller;

import com.ranajeet.resumeportal.model.Profile;
import com.ranajeet.resumeportal.service.ProfileService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(
            ProfileService profileService) {

        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(
            Authentication authentication,
            @RequestBody Profile profile) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                profileService.createProfile(
                        email,
                        profile
                )
        );
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile(
            Authentication authentication) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                profileService.getProfile(email)
        );
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(
            Authentication authentication,
            @RequestBody Profile profile) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                profileService.updateProfile(
                        email,
                        profile
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProfile(
            Authentication authentication) {

        String email =
                authentication.getName();

        profileService.deleteProfile(email);

        return ResponseEntity.ok(
                "Profile deleted successfully"
        );
    }
}