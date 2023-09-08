package com.zkflzl.myinit.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求
 *
 * @author <a href="https://gitee.com/zkflzl">zkflzl</a>
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String phone;

    private String userPassword;
}
