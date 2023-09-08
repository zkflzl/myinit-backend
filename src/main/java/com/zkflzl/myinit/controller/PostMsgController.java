package com.zkflzl.myinit.controller;

import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.model.dto.post.PostMsgUpdateRequest;
import com.zkflzl.myinit.service.PostMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;



/**
 * 帖子接口
 *
 * @author <a href="https://gitee.com/zkflzl">zkflzl</a>
 */

@Slf4j
@RestController
@RequestMapping("/post/msg")
public class PostMsgController {
    @Resource
    private PostMsgService postMsgService;


    /**
     * 点赞
     *
     * @param postId  postId
     * @param request 请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/update/thumb")
    public BaseResponse<Long> updateThumb(@RequestParam Long postId, HttpServletRequest request) {
        if (postId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        PostMsgUpdateRequest postMsgUpdateRequest = new PostMsgUpdateRequest();
        postMsgUpdateRequest.setPostId(postId);
        postMsgUpdateRequest.setType(1);

        return postMsgService.updatePostMsg(postMsgUpdateRequest, request);
    }

    /**
     * 收藏
     *
     * @param postId  postId
     * @param request 请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/update/favour")
    public BaseResponse<Long> updateFavour(@RequestParam Long postId, HttpServletRequest request) {
        if (postId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        PostMsgUpdateRequest postMsgUpdateRequest = new PostMsgUpdateRequest();
        postMsgUpdateRequest.setPostId(postId);
        postMsgUpdateRequest.setType(2);

        return postMsgService.updatePostMsg(postMsgUpdateRequest, request);
    }

    /**
     * 添加帖子转发
     *
     * @param postId  post id
     * @param request 请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @PostMapping("/add/forward")
    public BaseResponse<Long> addForward(@RequestParam Long postId, HttpServletRequest request) {
        if (postId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return postMsgService.addForward(postId, request);
    }
}
