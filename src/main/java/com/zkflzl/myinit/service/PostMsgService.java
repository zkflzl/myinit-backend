package com.zkflzl.myinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.model.dto.post.PostMsgUpdateRequest;
import com.zkflzl.myinit.model.entity.PostMsg;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 */
public interface PostMsgService extends IService<PostMsg> {

    /**
     * 更新帖子信息
     *
     * @param postMsgUpdateRequest 更新帖子信息参数
     * @param request              请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    BaseResponse<Long> updatePostMsg(PostMsgUpdateRequest postMsgUpdateRequest, HttpServletRequest request);

    /**
     * 帖子转发数量增加
     *
     * @param postId  帖子id
     * @param request 请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    BaseResponse<Long> addForward(Long postId, HttpServletRequest request);
}
