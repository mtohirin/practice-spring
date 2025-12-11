package com.gentara.school_attendance.siswa.repository;

import com.gentara.school_attendance.siswa.model.SiswaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiswaRepo extends JpaRepository<SiswaEntity, String> {
}
