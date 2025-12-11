package com.gentara.system_meatballs.exe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ExeController {
    @GetMapping
    public ModelAndView view (){
        return new ModelAndView("/pages/exe/index");
    }
}
