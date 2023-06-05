package com.zkflzl.myinit.service;

import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.model.dto.post.PostMsgUpdateRequest;
import com.zkflzl.myinit.model.entity.PostMsg;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 */
public interface PostMsgService extends IService<PostMsg> {

    BaseResponse<Long> updatePostMsg(PostMsgUpdateRequest postMsgUpdateRequest, HttpServletRequest request);
}
