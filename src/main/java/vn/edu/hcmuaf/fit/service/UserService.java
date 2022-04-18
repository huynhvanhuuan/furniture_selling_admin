package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.dto.user.UserCreate;
import vn.edu.hcmuaf.fit.entity.User;

public interface UserService {
    boolean register(UserCreate userCreate);

    User login(String email, String password);
    void updateProfile(User item);
    void delete(Long id);
    void lockUser(Long id);
}
