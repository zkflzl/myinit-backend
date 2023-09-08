package com.zkflzl.myinit.model.dto.user;

import com.zkflzl.myinit.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页获取用户请求
 *
 * @author <a href="https://gitee.com/zkflzl">zkflzl</a>
 */
@Data
public class UserListRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userRole;

    private String userName;

    private String phone;

    private String gender;
}
