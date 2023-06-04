package com.zkflzl.myinit.model.dto.post;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 */
@Data
public class PostDeleteRequest implements Serializable {

    /**
     * 帖子id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
