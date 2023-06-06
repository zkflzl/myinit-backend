package com.zkflzl.myinit.controller;

import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.model.dto.post.PostMsgUpdateRequest;
import com.zkflzl.myinit.service.PostMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



/**
 * 帖子接口
 *
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 */

@Slf4j
@Api(tags = "帖子信息管理")
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
    @ApiOperation("帖子点赞/取消点赞")
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
    @ApiOperation("帖子收藏/取消收藏")
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
}
