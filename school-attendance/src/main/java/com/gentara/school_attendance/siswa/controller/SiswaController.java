package com.gentara.school_attendance.siswa.controller;

import com.gentara.school_attendance.siswa.model.SiswaModel;
import com.gentara.school_attendance.siswa.service.SiswaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/siswa")
public class SiswaController {
    private SiswaService siswaService;

    public SiswaController(SiswaService siswaService){this.siswaService = siswaService;}

    @GetMapping
    public List<SiswaModel> getAll(){
        List<SiswaModel> result = this.siswaService.getAll();
        return result;
    }
    @GetMapping("/{id}")
    public SiswaModel getById(@PathVariable ("id")String id){
        SiswaModel result = this.siswaService.getById(id);
        return result;
    }
    @PostMapping
    public SiswaModel create(@RequestBody SiswaModel request){
        SiswaModel result = this.siswaService.save(request);
        return result;
    }
    @PatchMapping("/{id}")
    public SiswaModel edit(@PathVariable("id")String id, @RequestBody SiswaModel request){
        SiswaModel result = this.siswaService.update(id, request);
        return result;
    }
    @DeleteMapping("/{id}")
    public SiswaModel delete(@PathVariable("id")String id){
        SiswaModel result = this.siswaService.delete(id);
        return result;
    }
}
