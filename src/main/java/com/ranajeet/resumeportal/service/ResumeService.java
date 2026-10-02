package com.ranajeet.resumeportal.service;

import com.ranajeet.resumeportal.model.Resume;
import com.ranajeet.resumeportal.model.User;
import com.ranajeet.resumeportal.repository.ResumeRepository;
import com.ranajeet.resumeportal.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public ResumeService(
            ResumeRepository resumeRepository,
            UserRepository userRepository,
            S3Service s3Service) {

        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.s3Service = s3Service;
    }

    // =========================
    // UPLOAD RESUME
    // =========================

    public Resume uploadResume(
            String email,
            MultipartFile file) throws IOException {

        // Check file exists
        if (file == null || file.isEmpty()) {
            throw new RuntimeException(
                    "Resume file is required"
            );
        }

        // Check original filename
        String fileName = file.getOriginalFilename();

        if (fileName == null || fileName.isBlank()) {
            throw new RuntimeException(
                    "Invalid file name"
            );
        }

        // Only PDF allowed
        if (!fileName.toLowerCase().endsWith(".pdf")) {
            throw new RuntimeException(
                    "Only PDF files are allowed"
            );
        }

        // Maximum 5 MB
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException(
                    "Maximum file size is 5 MB"
            );
        }

        // Find logged-in user
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        // Upload file to S3
        String s3Key = s3Service.uploadFile(
                file,
                email
        );

        // Create database record
        Resume resume = new Resume();

        resume.setFileName(fileName);

        resume.setS3Key(s3Key);

        resume.setS3Url(
                "s3://" +
                bucketName +
                "/" +
                s3Key
        );

        resume.setUploadedAt(
                LocalDateTime.now()
        );

        resume.setUser(user);

        // Save metadata to PostgreSQL
        return resumeRepository.save(resume);
    }

    // =========================
    // GET USER RESUMES
    // =========================

    public List<Resume> getMyResumes(
            String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        return resumeRepository.findByUser(user);
    }

    // =========================
    // DELETE RESUME
    // =========================

    public void deleteResume(
            String email,
            Long resumeId) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        Resume resume = resumeRepository
                .findByIdAndUser(
                        resumeId,
                        user
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resume not found"
                        )
                );

        // Delete from AWS S3
        if (resume.getS3Key() != null &&
                !resume.getS3Key().isBlank()) {

            s3Service.deleteFile(
                    resume.getS3Key()
            );
        }

        // Delete database record
        resumeRepository.delete(resume);
    }
}