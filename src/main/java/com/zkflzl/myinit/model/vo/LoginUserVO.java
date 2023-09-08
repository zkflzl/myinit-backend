package com.zkflzl.myinit.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 已登录用户视图（脱敏）
 *
 * @author <a href="https://gitee.com/zkflzl">zkflzl</a>
 **/
@Data
public class LoginUserVO implements Serializable {
    /**
     * id
     */
    private Long id;

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
    private String userAvatar;

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
