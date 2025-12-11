package com.gentara.school_attendance.guru.controller;

import com.gentara.school_attendance.guru.model.GuruModel;
import com.gentara.school_attendance.guru.service.GuruService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/guru")
public class GuruControllerWeb {
    private GuruService guruService;

    public GuruControllerWeb(GuruService guruService){this.guruService = guruService;}
    @GetMapping
    public ModelAndView getAll(){
        ModelAndView view = new ModelAndView("/pages/guru/index");
        List<GuruModel> result = guruService.getAll();

        view.addObject("guru", result);
        return view;
    }
    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("/pages/guru/add");
        return view;
    }
    @PostMapping("save")
    public ModelAndView save(@ModelAttribute GuruModel guruModel){
        this.guruService.save(guruModel);
        return new ModelAndView("redirect:/guru");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id")String id){
        GuruModel guruModel = this.guruService.getById(id);
        if (guruModel == null){
            return new ModelAndView("redirect:/guru");
        }
        ModelAndView view = new ModelAndView("/pages/guru/edit");
        view.addObject("guru", guruModel);
        return view;
    }
    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute GuruModel guruModel){
        this.guruService.update(guruModel.getId(), guruModel);
        return new ModelAndView("redirect:/guru");
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id")String id){
        GuruModel guruModel= this.guruService.getById(id);
        if (guruModel == null){
            return new ModelAndView("redirect:/guru");
        }
        ModelAndView modelAndView = new ModelAndView("/pages/guru/delete");
        modelAndView.addObject("delete", guruModel);
        return modelAndView;
    }
    @PostMapping("/remove")
    public ModelAndView delete(@ModelAttribute GuruModel guruModel){
        this.guruService.delete(guruModel.getId());
        return new ModelAndView("redirect:/guru");
    }
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id")String id){
        GuruModel guruModel = this.guruService.getById(id);
        if (guruModel == null){
            return new ModelAndView("redirect:/guru");
        }
        ModelAndView modelAndView = new ModelAndView("/pages/guru/detail");
        modelAndView.addObject("detail", guruModel);
        return modelAndView;
    }
}

