package com.ranajeet.resumeportal.repository;

import com.ranajeet.resumeportal.model.Resume;
import com.ranajeet.resumeportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByUser(User user);

    Optional<Resume> findByIdAndUser(Long id, User user);
}