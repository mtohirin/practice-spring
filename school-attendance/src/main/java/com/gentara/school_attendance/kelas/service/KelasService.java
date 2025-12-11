package com.gentara.school_attendance.kelas.service;

import com.gentara.school_attendance.kelas.model.KelasModel;

import java.util.List;

public interface KelasService {
  List<KelasModel> getAll();
  KelasModel getById(String id);
  KelasModel save(KelasModel request);
  KelasModel update(String id, KelasModel request);
  KelasModel delete(String id);
}
