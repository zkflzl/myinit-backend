package com.zkflzl.myinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkflzl.myinit.annotation.AuthCheck;
import com.zkflzl.myinit.annotation.MyLog;
import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.common.ResultUtils;
import com.zkflzl.myinit.constant.UserConstant;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.exception.ThrowUtils;
import com.zkflzl.myinit.model.dto.user.UserListRequest;
import com.zkflzl.myinit.model.dto.user.UserLoginRequest;
import com.zkflzl.myinit.model.dto.user.UserRegisterRequest;
import com.zkflzl.myinit.model.entity.User;
import com.zkflzl.myinit.model.vo.LoginUserVO;
import com.zkflzl.myinit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String phone = userRegisterRequest.getPhone();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(phone, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(phone, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求
     * @param request          请求
     * @return {@link BaseResponse}<{@link LoginUserVO}>
     */
    @MyLog
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {

        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);

        String phone = userLoginRequest.getPhone();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(phone, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(phone, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return {@link BaseResponse}<{@link Object}>
     */
    @GetMapping("/current")
    public BaseResponse<Object> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);

        if (user == null) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        }

        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    /**
     * 退出登录
     *
     * @param request 请求
     * @return {@link BaseResponse}<{@link Object}>
     */
    @GetMapping("/logout")
    public BaseResponse<Object> userLogout(HttpServletRequest request) {
        return userService.userLogout(request);
    }

    /**
     * 分页获取用户列表（仅限管理员）
     * @param userListRequest
     * @param request
     * @return
     */
    @MyLog
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/list/page")
    public BaseResponse<Page<User>> userListByPage(@RequestBody UserListRequest userListRequest, HttpServletRequest request) {
        long current = userListRequest.getCurrent();
        long pageSize = userListRequest.getPageSize();

        ThrowUtils.throwIf(current<0,ErrorCode.PARAMS_ERROR);

        Page<User> userPage = userService.page(new Page<>(current, pageSize),
                userService.getQueryWrapper(userListRequest));
        return ResultUtils.success(userPage);
    }
}



