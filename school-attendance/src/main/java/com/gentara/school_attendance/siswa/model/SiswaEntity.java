package com.gentara.school_attendance.siswa.model;

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
@Table(name = "t_siswa")
public class SiswaEntity {
    @Id
    @Column
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "nis")
    private String nis;
}
