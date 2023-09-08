package com.zkflzl.myinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.model.dto.post.PostAddRequest;
import com.zkflzl.myinit.model.dto.post.PostDeleteRequest;
import com.zkflzl.myinit.model.dto.post.PostSelfGetRequest;
import com.zkflzl.myinit.model.dto.post.PostUpdateRequest;
import com.zkflzl.myinit.model.entity.Post;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author <a href="https://gitee.com/zkflzl">zkflzl</a>
 */
public interface PostService extends IService<Post> {
    BaseResponse<Long> addPost(PostAddRequest postAddRequest, HttpServletRequest request);

    BaseResponse<Boolean> deletePost(PostDeleteRequest postDeleteRequest, HttpServletRequest request);

    BaseResponse<Page<Post>> getSelfPost(PostSelfGetRequest postSelfGetRequest, HttpServletRequest request);

    BaseResponse<Boolean> updatePost(PostUpdateRequest postUpdateRequest, HttpServletRequest request);

    /**
     * 校验
     *
     * @param post
     * @param add
     */
    void validPost(Post post, boolean add);
}
