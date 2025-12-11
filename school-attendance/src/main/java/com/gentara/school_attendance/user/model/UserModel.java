package com.gentara.school_attendance.user.model;

import com.gentara.school_attendance.enumz.UserRole;
import com.gentara.school_attendance.enumz.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String id;
    private String username;
    private String password;
    private UserRole userRole;
    private UserStatus userStatus;
}
