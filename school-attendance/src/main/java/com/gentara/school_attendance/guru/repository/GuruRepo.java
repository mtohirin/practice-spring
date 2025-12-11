package com.gentara.school_attendance.guru.repository;

import com.gentara.school_attendance.guru.model.GuruEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuruRepo extends JpaRepository<GuruEntity, String>{
}
