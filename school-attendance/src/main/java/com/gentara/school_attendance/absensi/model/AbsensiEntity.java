package com.gentara.school_attendance.absensi.model;

import com.gentara.school_attendance.enumz.AbsensiStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_absensi")
public class AbsensiEntity {

    @Id
    @Column
    private String id;

    @Column(name = "tanggal")
    private LocalDateTime tanggal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AbsensiStatus status;
}
