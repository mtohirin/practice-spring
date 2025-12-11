package com.gentara.school_attendance.user.service;

import com.gentara.school_attendance.user.model.UserEntity;
import com.gentara.school_attendance.user.model.UserModel;
import com.gentara.school_attendance.user.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserSeriviceImpl implements UserService {
    private UserRepo userRepo;

    public UserSeriviceImpl(UserRepo userRepo){this.userRepo = userRepo;}

    @Override
    public List<UserModel> getAll() {
       List<UserEntity> result = this.userRepo.findAll();
       List<UserModel> listModel = new ArrayList<>();
       for (UserEntity entity : result){
           UserModel model = new UserModel();
           model.setId(entity.getId());
           model.setUsername(entity.getUsername());
           model.setPassword(entity.getPassword());
           model.setUserRole(entity.getUserRole());
           model.setUserStatus(entity.getUserStatus());
           listModel.add(model);
       }
       return listModel;
    }

    @Override
    public UserModel getById(String id) {
       UserEntity entity = this.userRepo.findById(id).orElse(null);
       UserModel result = new UserModel();
       result.setId(entity.getId());
       result.setUsername(entity.getUsername());
       result.setPassword(entity.getPassword());
       result.setUserRole(entity.getUserRole());
       result.setUserStatus(entity.getUserStatus());
       return result;
    }

    @Override
    public UserModel save(UserModel request) {
       UserEntity entity = new UserEntity();
       entity.setId(UUID.randomUUID().toString());
       entity.setUsername(request.getUsername());
       entity.setPassword(request.getPassword());
       entity.setUserRole(request.getUserRole());
       entity.setUserStatus(request.getUserStatus());

       try {
           this.userRepo.save(entity);
           log.info("save user success");
           UserModel response = new UserModel(entity.getId(), entity.getUsername(), entity.getPassword(), entity.getUserRole(), entity.getUserStatus());
           return response;
       }catch (Exception e){
           log.error("save user failed {}", e.getMessage());
           return new UserModel();
       }
    }

    @Override
    public UserModel update(String id, UserModel request) {
      UserEntity entity = this.userRepo.findById(id).orElse(null);
      entity.setUsername(request.getUsername());
      entity.setPassword(request.getPassword());
      entity.setUserRole(request.getUserRole());
      entity.setUserStatus(request.getUserStatus());

      try {
          this.userRepo.save(entity);
          log.info("update user success");
          UserModel response = new UserModel(entity.getId(), entity.getUsername(), entity.getPassword(), entity.getUserRole(), entity.getUserStatus());
          return response;
      }catch (Exception e){
          log.error("update user failed, error {}", e.getMessage());
          return new UserModel();
      }
    }

    @Override
    public UserModel delete(String id) {
      UserEntity entity = this.userRepo.findById(id).orElse(null);

      try {
          this.userRepo.delete(entity);
          log.info("delete user success");
          UserModel response = new UserModel(entity.getId(), entity.getUsername(), entity.getPassword(), entity.getUserRole(), entity.getUserStatus());
          return response;
      }catch (Exception e){
          log.error("delete user failed, error {}", e.getMessage());
          return new UserModel();
      }
    }
}
