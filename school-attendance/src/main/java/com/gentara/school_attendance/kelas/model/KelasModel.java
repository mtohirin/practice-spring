package com.gentara.school_attendance.kelas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KelasModel {
    private String id;
    private String namaKelas;
    private String waliKelas;
    private String tahunAjaran;
}
