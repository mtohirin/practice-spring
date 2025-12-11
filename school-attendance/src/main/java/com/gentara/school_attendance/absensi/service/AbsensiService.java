package com.gentara.school_attendance.absensi.service;

import com.gentara.school_attendance.absensi.model.AbsensiModel;

import java.util.List;

public interface AbsensiService {
    List<AbsensiModel> getAll();
    AbsensiModel getById(String id);
    AbsensiModel save(AbsensiModel request);
    AbsensiModel update(String id, AbsensiModel request);
    AbsensiModel delete(String id);

}
