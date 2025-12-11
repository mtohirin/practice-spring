package com.gentara.school_attendance.guru.controller;

import com.gentara.school_attendance.guru.model.GuruModel;
import com.gentara.school_attendance.guru.service.GuruService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guru")
public class GuruController {
    private GuruService guruService;

    public GuruController(GuruService guruService){this.guruService = guruService;}
    @GetMapping
    public List<GuruModel> getAll(){
        List<GuruModel> result = this.guruService.getAll();
        return result;
    }
    @GetMapping("/{id}")
    public GuruModel getById(@PathVariable ("id") String id){
        GuruModel result = this.guruService.getById(id);
        return result;
    }
    @PostMapping
    public GuruModel save(@RequestBody GuruModel response){
        GuruModel result = this.guruService.save(response);
        return result;
    }
    @PatchMapping("/{id}")
    public GuruModel update(@PathVariable ("id") String id, @RequestBody GuruModel request){
        GuruModel result = this.guruService.update(id, request);
        return result;
    }
    @DeleteMapping("/{id}")
    public GuruModel delete(@PathVariable ("id") String id){
        GuruModel result = this.guruService.delete(id);
        return result;
    }
}
