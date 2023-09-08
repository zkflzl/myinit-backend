package com.zkflzl.myinit.model.dto.post;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://gitee.com/zkflzl">zkflzl</a>
 */
@Data
public class PostDeleteRequest implements Serializable {

    /**
     * 帖子id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
