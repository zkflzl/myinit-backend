package com.zkflzl.myinit.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 已登录用户视图（脱敏）
 *
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 **/
@Data
public class LoginUserVO implements Serializable {

    /**
     * 昵称
     */
    private String userName;

    /**
     * 账户
     */
    private String account;

    /**
     * 头像
     */
    private String icon;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 权限
     */
    private String userRole;

    /**
     * 性别
     */
    private String gender;


    private static final long serialVersionUID = 1L;
}
