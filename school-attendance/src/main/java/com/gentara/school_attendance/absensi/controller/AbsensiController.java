package com.gentara.school_attendance.absensi.controller;

import com.gentara.school_attendance.absensi.model.AbsensiModel;
import com.gentara.school_attendance.absensi.service.AbsensiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/absensi")
public class AbsensiController {
    private AbsensiService absensiService;

    public AbsensiController(AbsensiService absensiService){this.absensiService = absensiService;}

    @GetMapping
    public List<AbsensiModel> getAll(){
        List<AbsensiModel> result = this.absensiService.getAll();
        return result;
    }
    @GetMapping("/{id}")
    public AbsensiModel getById(@PathVariable("id") String id){
        AbsensiModel result = this.absensiService.getById(id);
        return result;
    }
    @PostMapping
    public AbsensiModel create(@RequestBody AbsensiModel request){
        AbsensiModel result = this.absensiService.save(request);
        return result;
    }
    @PatchMapping("/{id}")
    public AbsensiModel edit(@PathVariable("id")String id, @RequestBody AbsensiModel request){
        AbsensiModel result = this.absensiService.update(id, request);
        return result;
    }
    @DeleteMapping("/{id}")
    public AbsensiModel delete(@PathVariable("id")String id){
        AbsensiModel result = this.absensiService.delete(id);
        return result;
    }
}