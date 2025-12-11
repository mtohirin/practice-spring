package com.gentara.school_attendance.user.controller;

import com.gentara.school_attendance.user.model.UserModel;
import com.gentara.school_attendance.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){this.userService = userService;}

    @GetMapping
    public List<UserModel> getAll(){
        List<UserModel> result = this.userService.getAll();
        return result;
    }
    @GetMapping("/{id}")
    public UserModel getById(@PathVariable("id") String id){
        UserModel result = this.userService.getById(id);
        return result;
    }
    @PostMapping
    public UserModel create(@RequestBody UserModel request){
        UserModel result = this.userService.save(request);
        return result;
    }
    @PatchMapping("/{id}")
    public UserModel edit(@PathVariable("id")String id, @RequestBody UserModel request){
        UserModel result = this.userService.update(id, request);
        return result;
    }
    @DeleteMapping("/{id}")
    public UserModel delete(@PathVariable("id")String id){
        UserModel result = this.userService.delete(id);
        return result;
    }
}
