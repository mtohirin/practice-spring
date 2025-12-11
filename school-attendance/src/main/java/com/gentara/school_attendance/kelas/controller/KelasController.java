package com.gentara.school_attendance.kelas.controller;

import com.gentara.school_attendance.kelas.model.KelasModel;
import com.gentara.school_attendance.kelas.service.KelasService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kelas")
public class KelasController {
    private KelasService kelasService;

    public KelasController(KelasService kelasService){this.kelasService = kelasService;}
    @GetMapping
    public List<KelasModel> getAll(){
        List<KelasModel> result = this.kelasService.getAll();
        return result;
    }
    @GetMapping("/{id}")
    public KelasModel getById(@PathVariable("id")String id){
        KelasModel result = this.kelasService.getById(id);
        return result;
    }
    @PostMapping
    public KelasModel create(@RequestBody KelasModel request){
        KelasModel result = this.kelasService.save(request);
        return result;
    }
    @PatchMapping("{id}")
    public KelasModel edit(@PathVariable("id")String id,@RequestBody KelasModel request){
        KelasModel result =  this.kelasService.update(id,request);
        return result;
    }
    @DeleteMapping("{id}")
    public KelasModel delete(@PathVariable("id")String id){
        KelasModel result = this.kelasService.delete(id);
        return result;
    }
}
