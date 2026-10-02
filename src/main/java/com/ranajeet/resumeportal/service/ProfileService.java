package com.ranajeet.resumeportal.service;

import com.ranajeet.resumeportal.model.Profile;
import com.ranajeet.resumeportal.model.User;
import com.ranajeet.resumeportal.repository.ProfileRepository;
import com.ranajeet.resumeportal.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(
            ProfileRepository profileRepository,
            UserRepository userRepository) {

        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public Profile createProfile(
            String email,
            Profile profileData) {

        User user = getUser(email);

        if (profileRepository.findByUser(user).isPresent()) {
            throw new RuntimeException(
                    "Profile already exists"
            );
        }

        profileData.setUser(user);

        return profileRepository.save(profileData);
    }

    public Profile getProfile(String email) {

        User user = getUser(email);

        return profileRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Profile not found"
                        )
                );
    }

    public Profile updateProfile(
            String email,
            Profile updatedProfile) {

        Profile existingProfile =
                getProfile(email);

        existingProfile.setPhone(
                updatedProfile.getPhone()
        );

        existingProfile.setLocation(
                updatedProfile.getLocation()
        );

        existingProfile.setLinkedin(
                updatedProfile.getLinkedin()
        );

        existingProfile.setGithub(
                updatedProfile.getGithub()
        );

        existingProfile.setSkills(
                updatedProfile.getSkills()
        );

        existingProfile.setSummary(
                updatedProfile.getSummary()
        );

        return profileRepository.save(
                existingProfile
        );
    }

    public void deleteProfile(String email) {

        Profile profile = getProfile(email);

        profileRepository.delete(profile);
    }

    private User getUser(String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );
    }
}