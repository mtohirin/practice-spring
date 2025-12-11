package com.gentara.school_attendance.kelas.repository;

import com.gentara.school_attendance.kelas.model.KelasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KelasRepo extends JpaRepository<KelasEntity, String> {
}
