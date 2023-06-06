package com.zkflzl.myinit.model.dto.post;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 */
@Data
public class PostSelfGetRequest implements Serializable {

    /**
     * 页码
     */
    private Integer pageName;

    /**
     * 页长
     */
    private Integer pageSize;


    private static final long serialVersionUID = 1L;
}
