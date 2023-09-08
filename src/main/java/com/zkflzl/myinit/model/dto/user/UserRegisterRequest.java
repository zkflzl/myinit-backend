package com.zkflzl.myinit.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author <a href="https://gitee.com/zkflzl">zkflzl</a>
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String phone;

    private String userPassword;

    private String checkPassword;
}
