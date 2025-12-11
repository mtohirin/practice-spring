package com.gentara.school_attendance.absensi.service;

import com.gentara.school_attendance.absensi.model.AbsensiEntity;
import com.gentara.school_attendance.absensi.model.AbsensiModel;
import com.gentara.school_attendance.absensi.repository.AbsensiRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AbsensiServiceImpl implements AbsensiService {
    private AbsensiRepo absensiRepo;

    public AbsensiServiceImpl(AbsensiRepo absensiRepo){this.absensiRepo = absensiRepo;}

    @Override
    public List<AbsensiModel> getAll() {
       List<AbsensiEntity> result = this.absensiRepo.findAll();
       List<AbsensiModel> listModel = new ArrayList<>();
       for (AbsensiEntity entity : result){
           AbsensiModel model = new AbsensiModel();
           model.setId(entity.getId());
           model.setTanggal(entity.getTanggal());
           model.setStatus(entity.getStatus());
           listModel.add(model);
       }
       return listModel;
    }

    @Override
    public AbsensiModel getById(String id) {
       AbsensiEntity entity = this.absensiRepo.findById(id).orElse(null);
       AbsensiModel result = new AbsensiModel();
       result.setId(entity.getId());
       result.setTanggal(entity.getTanggal());
       result.setStatus(entity.getStatus());
       return result;
    }

    @Override
    public AbsensiModel save(AbsensiModel request) {
      AbsensiEntity entity = new AbsensiEntity();
      entity.setId(UUID.randomUUID().toString());
      entity.setTanggal(request.getTanggal());
      entity.setStatus(request.getStatus());

      try {
          this.absensiRepo.save(entity);
          log.info("save absensi success");
          AbsensiModel response = new AbsensiModel(entity.getId(), entity.getTanggal(), entity.getStatus());
          return response;
      }catch (Exception e){
          log.error("save absensi failed, error {}", e.getMessage());
          return new AbsensiModel();
      }
    }

    @Override
    public AbsensiModel update(String id, AbsensiModel request) {
        AbsensiEntity entity = this.absensiRepo.findById(id).orElse(null);
        entity.setTanggal(request.getTanggal());
        entity.setStatus(request.getStatus());

        try {
            this.absensiRepo.save(entity);
            log.info("update absensi success");
            AbsensiModel response = new AbsensiModel(entity.getId(), entity.getTanggal(), entity.getStatus());
            return response;
        }catch (Exception e){
            log.error("update absensi failed, error {}", e.getMessage());
            return new AbsensiModel();
        }
    }

    @Override
    public AbsensiModel delete(String id) {
       AbsensiEntity entity = this.absensiRepo.findById(id).orElse(null);

       try {
           this.absensiRepo.delete(entity);
           log.info("delete absensi success ");
           AbsensiModel response = new AbsensiModel(entity.getId(), entity.getTanggal(), entity.getStatus());
           return response;
       }catch (Exception e){
           log.error("delete absensi failed, error");
           return new AbsensiModel();
       }
    }
}
