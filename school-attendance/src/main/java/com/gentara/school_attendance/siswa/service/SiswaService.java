package com.gentara.school_attendance.siswa.service;

import com.gentara.school_attendance.siswa.model.SiswaModel;

import java.util.List;

public interface SiswaService {
    List<SiswaModel> getAll();
    SiswaModel getById(String id);
    SiswaModel save(SiswaModel request);
    SiswaModel update(String id, SiswaModel request);
    SiswaModel delete(String id);
}
