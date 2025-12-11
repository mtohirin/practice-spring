package com.gentara.school_attendance.absensi.repository;

import com.gentara.school_attendance.absensi.model.AbsensiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsensiRepo extends JpaRepository<AbsensiEntity, String> {
}
