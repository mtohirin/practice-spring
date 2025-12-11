package com.gentara.school_attendance.user.service;

import com.gentara.school_attendance.user.model.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> getAll();
    UserModel getById(String id);
    UserModel save(UserModel request);
    UserModel update(String id, UserModel request);
    UserModel delete(String id);
}
