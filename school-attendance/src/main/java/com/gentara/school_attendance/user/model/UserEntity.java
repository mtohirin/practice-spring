package com.gentara.school_attendance.user.model;

import com.gentara.school_attendance.enumz.UserRole;
import com.gentara.school_attendance.enumz.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
public class UserEntity {
    @Id
    @Column
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus;


}
