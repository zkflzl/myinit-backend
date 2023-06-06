package com.zkflzl.myinit.model.dto.post;

import lombok.Data;

import java.io.Serializable;

/**
 * 点赞、取消点赞请求
 *
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 */
@Data
public class PostMsgUpdateRequest implements Serializable {

    /**
     * 帖子 id
     */
    private Long postId;

    /**
     * 类型（1、点赞  2、收藏）
     */
    private Integer type;


    private static final long serialVersionUID = 1L;
}
