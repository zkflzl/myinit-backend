package com.zkflzl.myinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkflzl.myinit.model.entity.User;

/**
 *
 */
public interface UserService extends IService<User> {

    long userRegister(String phone, String userPassword, String checkPassword);
}
