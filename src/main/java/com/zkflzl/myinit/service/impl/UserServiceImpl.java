package com.zkflzl.myinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.model.entity.User;
import com.zkflzl.myinit.service.UserService;
import com.zkflzl.myinit.mapper.UserMapper;
import com.zkflzl.myinit.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 *
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final String SALT = "ZKFLZL";
    private final String MARKED = "INIT_";


    @Override
    public long userRegister(String phone, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(phone, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (RegexUtils.isPhoneInvalid(phone)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "手机号码不符合规范");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (phone.intern()) {
            // 注册手机号码不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();

            String tempAccount = MARKED + DigestUtils.md5DigestAsHex((MARKED + phone).getBytes());
            user.setAccount(tempAccount);

            user.setPhone(phone);
            user.setPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }
}




