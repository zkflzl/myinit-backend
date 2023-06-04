package com.zkflzl.myinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkflzl.myinit.model.entity.User;
import com.zkflzl.myinit.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *  @author <a href="https://github.com/zkflzl">程序员zk</a>
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param phone   用户手机号码
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String phone, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param phone  用户手机号码
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String phone, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    Object getLoginUserVO(User user);

    boolean isAdmin(HttpServletRequest request);

}
