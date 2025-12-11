package com.gentara.school_attendance.guru.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuruModel {
    private String id;
    private String namaGuru;
    private String nip;
    private String mataPelajaran;
}
