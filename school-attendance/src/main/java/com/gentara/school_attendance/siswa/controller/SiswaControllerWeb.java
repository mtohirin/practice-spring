package com.gentara.school_attendance.siswa.controller;

import com.gentara.school_attendance.siswa.model.SiswaModel;
import com.gentara.school_attendance.siswa.service.SiswaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/siswa")
public class SiswaControllerWeb {
    private SiswaService siswaService;

    public SiswaControllerWeb(SiswaService siswaService){this.siswaService = siswaService;}
    @GetMapping
    public ModelAndView getAll(){
        ModelAndView view = new ModelAndView("/pages/siswa/index");
        List<SiswaModel> result = siswaService.getAll();

        view.addObject("siswa", result);
        return view;
    }
    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("/pages/siswa/add");
        return view;
    }
    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute SiswaModel siswaModel){
        this.siswaService.save(siswaModel);
        return new ModelAndView("redirect:/siswa");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable ("id")String id){
        SiswaModel siswaModel = this.siswaService.getById(id);
        if (siswaModel == null){
            return new ModelAndView("redirect:/siswa");
        }
        ModelAndView view = new ModelAndView("/pages/siswa/edit");
        view.addObject("siswa", siswaModel);
        return view;

    }
    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute SiswaModel siswaModel){
        this.siswaService.update(siswaModel.getId(), siswaModel);
        return new ModelAndView("redirect:/siswa");
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id")String id){
        SiswaModel siswaModel = this.siswaService.getById(id);
        if (siswaModel == null){
            return new ModelAndView("redirect:/siswa");
        }
        ModelAndView view = new ModelAndView("/pages/siswa/delete");
        view.addObject("delete", siswaModel);
        return view;
    }
    @PostMapping("/remove")
    public ModelAndView delete(@ModelAttribute SiswaModel siswaModel){
        this.siswaService.delete(siswaModel.getId());
        return new ModelAndView("redirect:/siswa");
    }
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id")String id){
        SiswaModel siswaModel = this.siswaService.getById(id);
        if (siswaModel == null){
            return new ModelAndView("redirect:/siswa");
        }
        ModelAndView view = new ModelAndView("/pages/siswa/detail");
        view.addObject("detail", siswaModel);
        return view;
    }
}
