package com.gentara.school_attendance.absensi.model;

import com.gentara.school_attendance.enumz.AbsensiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbsensiModel {
    private String id;
    private LocalDateTime tanggal;
    private AbsensiStatus status;
}
