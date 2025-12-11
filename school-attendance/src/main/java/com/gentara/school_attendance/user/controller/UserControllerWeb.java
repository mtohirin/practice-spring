package com.gentara.school_attendance.user.controller;

import com.gentara.school_attendance.enumz.UserRole;
import com.gentara.school_attendance.enumz.UserStatus;
import com.gentara.school_attendance.user.model.UserModel;
import com.gentara.school_attendance.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserControllerWeb {
    private UserService userService;

    public UserControllerWeb(UserService userService){this.userService = userService;}
    @GetMapping
    public ModelAndView getAll(){
        ModelAndView view = new ModelAndView("/pages/user/index");
        List<UserModel> result = userService.getAll();

        view.addObject("users", result);
        return view;
    }
    @GetMapping("/add")
    public ModelAndView add(){
       ModelAndView view = new ModelAndView("/pages/user/add");

       view.addObject("roles", UserRole.values());
       view.addObject("statuses", UserStatus.values());

       return view;
    }
    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute UserModel userModel){
        this.userService.save(userModel);
        return new ModelAndView("redirect:/user");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id")String id){
        UserModel userModel = this.userService.getById(id);
        if (userModel == null){
            return new ModelAndView("redirect:/user");
        }
        ModelAndView view = new ModelAndView("/pages/user/edit");
        view.addObject("user", userModel);
        view.addObject("roles", UserRole.values());
        view.addObject("statuses", UserStatus.values());
        return view;
    }
    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute UserModel userModel){
        this.userService.update(userModel.getId(), userModel);
        return new ModelAndView("redirect:/user");
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id")String id){
        UserModel userModel = this.userService.getById(id);
        if (userModel == null){
            return new ModelAndView("redirect:/user");
        }
        ModelAndView modelAndView = new ModelAndView("/pages/user/delete");
        modelAndView.addObject("delete", userModel);
        return modelAndView;
    }
    @PostMapping("/remove")
    public ModelAndView delete(@ModelAttribute UserModel userModel){
        this.userService.delete(userModel.getId());
        return new ModelAndView("redirect:/user");
    }
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id")String id){
        UserModel userModel = this.userService.getById(id);
        if (userModel == null){
            return new ModelAndView("redirect:/user");
        }
        ModelAndView modelAndView = new ModelAndView("/pages/user/detail");
        modelAndView.addObject("detail", userModel);
        return modelAndView;
    }
}
