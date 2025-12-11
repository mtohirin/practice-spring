package com.gentara.school_attendance.kelas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_kelas")
public class KelasEntity {
    @Id
    @Column
    private String id;

    @Column(name = "nama_kelas")
    private String namaKelas;

    @Column(name = "wali_kelas")
    private String waliKelas;

    @Column(name = "tahunAjaran")
    private String tahunAjaran;


}
