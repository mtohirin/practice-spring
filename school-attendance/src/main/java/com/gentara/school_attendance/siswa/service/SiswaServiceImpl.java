package com.gentara.school_attendance.siswa.service;

import com.gentara.school_attendance.siswa.model.SiswaEntity;
import com.gentara.school_attendance.siswa.model.SiswaModel;
import com.gentara.school_attendance.siswa.repository.SiswaRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SiswaServiceImpl implements SiswaService{
    private SiswaRepo siswaRepo;

    public SiswaServiceImpl (SiswaRepo siswaRepo){this.siswaRepo = siswaRepo;}

    @Override
    public List<SiswaModel> getAll() {
      List<SiswaEntity> result = this.siswaRepo.findAll();
      List<SiswaModel> listModel = new ArrayList<>();
      for (SiswaEntity entity : result){
          SiswaModel model = new SiswaModel();
          model.setId(entity.getId());
          model.setName(entity.getName());
          model.setNis(entity.getNis());
          listModel.add(model);
      }
      return listModel;
    }

    @Override
    public SiswaModel getById(String id) {
        SiswaEntity entity = this.siswaRepo.findById(id).orElse(null);
        SiswaModel result = new SiswaModel();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setNis(entity.getNis());
        return result;
    }

    @Override
    public SiswaModel save(SiswaModel request) {
       SiswaEntity entity = new SiswaEntity();
       entity.setId(UUID.randomUUID().toString());
       entity.setName(request.getName());
       entity.setNis(request.getNis());

       try {
           this.siswaRepo.save(entity);
           log.info("save siswa success");
           SiswaModel response = new SiswaModel(entity.getId(), entity.getName(), entity.getNis());
           return response;
       }catch (Exception e){
           log.error("save siswa failed, error {}",e.getMessage());
           return new SiswaModel();
       }
    }

    @Override
    public SiswaModel update(String id, SiswaModel request) {
     SiswaEntity entity = this.siswaRepo.findById(id).orElse(null);
     entity.setName(request.getName());
     entity.setNis(request.getNis());

     try {
         this.siswaRepo.save(entity);
         log.info("update siswa success");
         SiswaModel response = new SiswaModel(entity.getId(), entity.getName(), entity.getNis());
         return response;
     }catch (Exception e){
         log.error("update siswa failed, error");
         return new SiswaModel();
     }
    }

    @Override
    public SiswaModel delete(String id) {
        SiswaEntity entity = this.siswaRepo.findById(id).orElse(null);

        try {
            this.siswaRepo.delete(entity);
            log.info("delete siswa success");
            SiswaModel response = new SiswaModel(entity.getId(), entity.getName(), entity.getNis());
            return response;
        }catch (Exception e){
            log.error("delete siswa failed, error {}",e.getMessage());
            return new SiswaModel();
        }
    }
}
