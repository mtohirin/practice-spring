package com.gentara.school_attendance.guru.model;

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
@Table(name = "t_guru")
public class GuruEntity {
    @Id
    @Column
    private String id;

    @Column(name = "nama_guru")
    private String nameGuru;

    @Column(name = "nip")
    private String nip;

    @Column(name = "mata_pelajaran")
    private String mataPelajaran;
}
