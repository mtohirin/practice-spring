package com.gentara.school_attendance.guru.service;

import com.gentara.school_attendance.guru.model.GuruModel;

import java.util.List;

public interface GuruService {
    List<GuruModel> getAll();
    GuruModel getById(String id);
    GuruModel save(GuruModel request);
    GuruModel update(String id, GuruModel request);
    GuruModel delete(String id);
}
