package com.gentara.school_attendance.absensi.controller;

import com.gentara.school_attendance.absensi.model.AbsensiModel;
import com.gentara.school_attendance.absensi.service.AbsensiService;
import com.gentara.school_attendance.enumz.AbsensiStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/absensi")
public class AbsensiControllerWeb {
    private AbsensiService absensiService;

    public AbsensiControllerWeb (AbsensiService absensiService){this.absensiService = absensiService;}
    @GetMapping
    public ModelAndView getall(){
        ModelAndView view = new ModelAndView("/pages/absensi/index");
        List<AbsensiModel> result = absensiService.getAll();

        view.addObject("absensi", result);
        return view;
    }
    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("/pages/absensi/add");

        view.addObject("statuses", AbsensiStatus.values());
        return view;
    }
    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute AbsensiModel absensiModel){
        this.absensiService.save(absensiModel);
        return new ModelAndView("redirect:/absensi");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id")String id){
        AbsensiModel absensiModel = this.absensiService.getById(id);
        if (absensiModel == null){
            return new ModelAndView("redirect:/absensi");
        }
        ModelAndView view = new ModelAndView("/pages/absensi/edit");
        view.addObject("edit", absensiModel);
        view.addObject("statuses", AbsensiStatus.values());
        return view;
    }
    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute AbsensiModel absensiModel){
        this.absensiService.update(absensiModel.getId(), absensiModel);
        return new ModelAndView("redirect:/absensi");
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id")String id){
        AbsensiModel absensiModel = this.absensiService.delete(id);
        if (absensiModel == null){
            return new ModelAndView("redirect:/absensi");
        }
        ModelAndView view = new ModelAndView("/pages/absensi/delete");
        view.addObject("delete", absensiModel);
        return view;
    }
    @PostMapping("/remove")
    public ModelAndView delete(@ModelAttribute AbsensiModel absensiModel){
        this.absensiService.delete(absensiModel.getId());
        return new ModelAndView("redirect:/absensi");
    }
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id")String id){
        AbsensiModel absensiModel = this.absensiService.getById(id);
        if (absensiModel == null){
            return new ModelAndView("redirect:/absensi");
        }
        ModelAndView view = new ModelAndView("/pages/absensi/detail");
        view.addObject("detail", absensiModel);
        return view;
    }
}
