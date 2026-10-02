package com.ranajeet.resumeportal.controller;

import com.ranajeet.resumeportal.model.Resume;
import com.ranajeet.resumeportal.service.ResumeService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(
            ResumeService resumeService) {

        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Resume> uploadResume(
            Authentication authentication,
            @RequestParam("file") MultipartFile file)
            throws IOException {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                resumeService.uploadResume(
                        email,
                        file
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getMyResumes(
            Authentication authentication) {

        String email =
                authentication.getName();

        return ResponseEntity.ok(
                resumeService.getMyResumes(
                        email
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(
            Authentication authentication,
            @PathVariable Long id) {

        String email =
                authentication.getName();

        resumeService.deleteResume(
                email,
                id
        );

        return ResponseEntity.ok(
                "Resume deleted successfully"
        );
    }
}