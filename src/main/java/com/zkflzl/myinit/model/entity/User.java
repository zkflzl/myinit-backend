package com.zkflzl.myinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @TableName user_info
 */
@TableName(value ="user_info")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 账户
     */
    private String account;

    /**
     * 昵称
     */
    private String userName;

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
