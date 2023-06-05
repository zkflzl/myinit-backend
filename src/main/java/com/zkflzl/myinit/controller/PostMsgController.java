package com.zkflzl.myinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.model.dto.post.*;
import com.zkflzl.myinit.model.entity.Post;
import com.zkflzl.myinit.service.PostMsgService;
import com.zkflzl.myinit.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


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
     * 帖子点赞、取消点赞
     *
     * @param postMsgUpdateRequest
     * @param request
     * @return
     * @author zk
     */
    @ApiOperation("帖子点赞/取消点赞")
    @PostMapping("/add/thumb")
    public BaseResponse<Long> addPost(@RequestBody PostMsgUpdateRequest postMsgUpdateRequest, HttpServletRequest request) {
        if (postMsgUpdateRequest == null || postMsgUpdateRequest.getPostId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return postMsgService.updatePostMsg(postMsgUpdateRequest, request);
    }
}
