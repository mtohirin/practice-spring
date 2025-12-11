package com.gentara.school_attendance.kelas.controller;

import com.gentara.school_attendance.kelas.model.KelasModel;
import com.gentara.school_attendance.kelas.service.KelasService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/kelas")
public class KelasControllerWeb {
    private KelasService kelasService;

    public KelasControllerWeb (KelasService kelasService){this.kelasService = kelasService;}
    @GetMapping
    public ModelAndView getall(){
        ModelAndView view = new ModelAndView("/pages/kelas/index");
        List<KelasModel> result = kelasService.getAll();

        view.addObject("kelas", result);
        return view;
    }
    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("/pages/kelas/add");
        return view;
    }
    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute KelasModel kelasModel){
        this.kelasService.save(kelasModel);
        return new ModelAndView("redirect:/kelas");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id")String id){
        KelasModel kelasModel = this.kelasService.getById(id);
        if (kelasModel == null){
            return new ModelAndView("redirect:/kelas");
        }
        ModelAndView view = new ModelAndView("/pages/kelas/edit");
        view.addObject("kelas", kelasModel);
        return view;
    }
    @PostMapping("update")
    public ModelAndView update(@ModelAttribute KelasModel kelasModel){
        this.kelasService.update(kelasModel.getId(), kelasModel);
        return new ModelAndView("redirect:/kelas");
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id")String id){
        KelasModel kelasModel = this.kelasService.delete(id);
        if (kelasModel == null){
            return new ModelAndView("redirect:/kelas");
        }
        ModelAndView view = new ModelAndView("/pages/kelas/delete");
        view.addObject("delete", kelasModel);
        return view;
    }
    @PostMapping("/remove")
    public ModelAndView delete(@ModelAttribute KelasModel kelasModel){
        this.kelasService.delete(kelasModel.getId());
        return new ModelAndView("redirect:/kelas");
    }
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id")String id){
        KelasModel kelasModel = this.kelasService.getById(id);
        if (kelasModel == null){
            return new ModelAndView("redirect:/kelas");
        }
        ModelAndView view = new ModelAndView("/pages/kelas/detail");
        view.addObject("detail", kelasModel);
        return view;
    }
}
