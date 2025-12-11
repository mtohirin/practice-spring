package com.gentara.school_attendance.kelas.service;

import com.gentara.school_attendance.kelas.model.KelasEntity;
import com.gentara.school_attendance.kelas.model.KelasModel;
import com.gentara.school_attendance.kelas.repository.KelasRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@Slf4j
public class KelasServiceImpl implements KelasService{

    private  KelasRepo kelasRepo;

    public KelasServiceImpl (KelasRepo kelasRepo){this.kelasRepo = kelasRepo;}

    @Override
    public List<KelasModel> getAll() {
        List<KelasEntity> result = this.kelasRepo.findAll();
        List<KelasModel> listModel = new ArrayList<>();
        for (KelasEntity entity : result){
            KelasModel model = new KelasModel();
            model.setId(entity.getId());
            model.setNamaKelas(entity.getNamaKelas());
            model.setWaliKelas(entity.getWaliKelas());
            model.setTahunAjaran(entity.getTahunAjaran());
            listModel.add( model);
        }
        return listModel;
    }

    @Override
    public KelasModel getById(String id) {
        KelasEntity entity = this.kelasRepo.findById(id).orElse(null);
        KelasModel result = new KelasModel();
        result.setId(entity.getId());
        result.setNamaKelas(entity.getNamaKelas());
        result.setWaliKelas(entity.getWaliKelas());
        result.setTahunAjaran(entity.getTahunAjaran());
        return result;
    }

    @Override
    public KelasModel save(KelasModel request) {
        KelasEntity entity = new KelasEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setNamaKelas(request.getNamaKelas());
        entity.setWaliKelas(request.getWaliKelas());
        entity.setTahunAjaran(request.getTahunAjaran());

        try {
            this.kelasRepo.save(entity);
            log.info("save kelas success");
            KelasModel response = new KelasModel(entity.getId(), entity.getNamaKelas(), entity.getWaliKelas(), entity.getTahunAjaran());
            return response;
        }catch (Exception e){
            log.error("save kelas failed, error {}",e.getMessage());
            return new KelasModel();
        }
    }

    @Override
    public KelasModel update(String id, KelasModel request) {
        KelasEntity entity = this.kelasRepo.findById(id).orElse(null);
        entity.setNamaKelas(request.getNamaKelas());
        entity.setWaliKelas(request.getWaliKelas());
        entity.setTahunAjaran(request.getTahunAjaran());

        try {
            this.kelasRepo.save(entity);
            log.info("update kelas success");
            KelasModel response = new KelasModel(entity.getId(), entity.getNamaKelas(), entity.getWaliKelas(), entity.getTahunAjaran());
            return response;
        }catch (Exception e){
            log.error("update kelas failed, error {}",e.getMessage());
            return new KelasModel();
        }
    }

    @Override
    public KelasModel delete(String id) {
        KelasEntity entity = this.kelasRepo.findById(id).orElse(null);

        try {
            this.kelasRepo.delete(entity);
            log.info("delete kelas success");
            KelasModel response = new KelasModel(entity.getId(), entity.getNamaKelas(), entity.getWaliKelas(), entity.getTahunAjaran());
            return response;
        }catch (Exception e){
            log.error("delete kelas failed, error");
            return new KelasModel();
        }
    }
}
