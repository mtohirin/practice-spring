package com.gentara.school_attendance.guru.service;

import com.gentara.school_attendance.guru.model.GuruEntity;
import com.gentara.school_attendance.guru.model.GuruModel;
import com.gentara.school_attendance.guru.repository.GuruRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GuruServiceImpl implements GuruService{
    private GuruRepo guruRepo;

    public GuruServiceImpl(GuruRepo guruRepo){this.guruRepo = guruRepo;}

    @Override
    public List<GuruModel> getAll() {
       List<GuruEntity> result = this.guruRepo.findAll();
       List<GuruModel> listModel = new ArrayList<>();
       for (GuruEntity entity : result){
           GuruModel model = new GuruModel();
           model.setId(entity.getId());
           model.setNamaGuru(entity.getNameGuru());
           model.setNip(entity.getNip());
           model.setMataPelajaran(entity.getMataPelajaran());
           listModel.add(model);
       }
       return listModel;
    }

    @Override
    public GuruModel getById(String id) {
        GuruEntity entity = this.guruRepo.findById(id).orElse(null);
        GuruModel result = new GuruModel();
        result.setId(entity.getId());
        result.setNamaGuru(entity.getNameGuru());
        result.setNip(entity.getNip());
        result.setMataPelajaran(entity.getMataPelajaran());
        return result;
    }

    @Override
    public GuruModel save(GuruModel request) {
        GuruEntity entity = new GuruEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setNameGuru(request.getNamaGuru());
        entity.setNip(request.getNip());
        entity.setMataPelajaran(request.getMataPelajaran());

        try {
            this.guruRepo.save(entity);
            log.info("save guru success");
            GuruModel response = new GuruModel(entity.getId(), entity.getNameGuru(), entity.getNip(), entity.getMataPelajaran());
            return response;
        }catch (Exception e){
            log.error("save guru failed, error {}",e.getMessage());
            return new GuruModel();
        }
    }

    @Override
    public GuruModel update(String id, GuruModel request) {
        GuruEntity entity = this.guruRepo.findById(id).orElse(null);
        entity.setNameGuru(request.getNamaGuru());
        entity.setNip(request.getNip());
        entity.setMataPelajaran(request.getMataPelajaran());

        try {
            this.guruRepo.save(entity);
            log.info("update guru success");
            GuruModel response = new GuruModel(entity.getId(), entity.getNameGuru(), entity.getNip(), entity.getMataPelajaran());
            return response;
        }catch (Exception e){
            log.error("update guru failed, error {}",e.getMessage());
            return new GuruModel();
        }
    }

    @Override
    public GuruModel delete(String id) {
        GuruEntity entity = this.guruRepo.findById(id).orElse(null);

        try {
            this.guruRepo.delete(entity);
            log.info("delete guru success");
            GuruModel response = new GuruModel(entity.getId(), entity.getNameGuru(), entity.getNip(), entity.getMataPelajaran());
            return response;
        }catch (Exception e){
            log.error("delete guru failed, error {}",e.getMessage());
            return new GuruModel();
        }
    }
}
