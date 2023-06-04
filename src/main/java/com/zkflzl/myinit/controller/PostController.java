package com.zkflzl.myinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.model.dto.post.PostAddRequest;
import com.zkflzl.myinit.model.dto.post.PostDeleteRequest;
import com.zkflzl.myinit.model.dto.post.PostSelfGetRequest;
import com.zkflzl.myinit.model.entity.Post;
import com.zkflzl.myinit.service.PostService;
import com.zkflzl.myinit.service.UserService;
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
@Api(tags = "帖子管理")
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;

    // region 增删改查


    // endregion

    /**
     * 创建
     *
     * @param postAddRequest
     * @param request
     * @return
     */
    @ApiOperation("添加帖子")
    @PostMapping("/add")
    public BaseResponse<Long> addPost(@RequestBody PostAddRequest postAddRequest, HttpServletRequest request) {
        if (postAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return postService.addPost(postAddRequest,request);
    }

    @ApiOperation("删除帖子")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePost(@RequestBody PostDeleteRequest postDeleteRequest, HttpServletRequest request) {
        if (postDeleteRequest == null || postDeleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return postService.deletePost(postDeleteRequest,request);
    }

    @ApiOperation("查看自己帖子（分页）")
    @PostMapping("/get/self")
    public BaseResponse<Page<Post>> getSelfPost(@RequestBody PostSelfGetRequest postSelfGetRequest, HttpServletRequest request) {
        if (postSelfGetRequest == null || postSelfGetRequest.getPageName() < 0 || postSelfGetRequest.getPageSize() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return postService.getSelfPost(postSelfGetRequest,request);
    }

}
