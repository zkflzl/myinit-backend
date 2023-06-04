package com.zkflzl.myinit.service;

import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.model.dto.post.PostAddRequest;
import com.zkflzl.myinit.model.dto.post.PostDeleteRequest;
import com.zkflzl.myinit.model.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 */
public interface PostService extends IService<Post> {
    BaseResponse<Long> addPost(PostAddRequest postAddRequest, HttpServletRequest request);

    BaseResponse<Boolean> deletePost(PostDeleteRequest postDeleteRequest, HttpServletRequest request);
}
