package com.zkflzl.myinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.common.ResultUtils;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.exception.ThrowUtils;
import com.zkflzl.myinit.model.dto.post.PostAddRequest;
import com.zkflzl.myinit.model.dto.post.PostDeleteRequest;
import com.zkflzl.myinit.model.dto.post.PostSelfGetRequest;
import com.zkflzl.myinit.model.dto.post.PostUpdateRequest;
import com.zkflzl.myinit.model.entity.Post;
import com.zkflzl.myinit.model.entity.User;
import com.zkflzl.myinit.service.PostService;
import com.zkflzl.myinit.mapper.PostMapper;
import com.zkflzl.myinit.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;


/**
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    @Override
    public BaseResponse<Long> addPost(PostAddRequest postAddRequest, HttpServletRequest request) {
        Post post = new Post();
        BeanUtils.copyProperties(postAddRequest, post);
        List<String> tags = postAddRequest.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        validPost(post, true);
        User loginUser = userService.getLoginUser(request);
        post.setUserId(loginUser.getId());
        post.setFavourNum(0);
        post.setThumbNum(0);
        boolean result = postService.save(post);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newPostId = post.getId();
        return ResultUtils.success(newPostId);
    }

    @Override
    public BaseResponse<Boolean> deletePost(PostDeleteRequest postDeleteRequest, HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        long id = postDeleteRequest.getId();
        // 判断是否存在
        Post oldPost = postService.getById(id);
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldPost.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = postService.removeById(id);
        return ResultUtils.success(b);
    }

    @Override
    public BaseResponse<Page<Post>> getSelfPost(PostSelfGetRequest postSelfGetRequest, HttpServletRequest request) {
        User user = userService.getLoginUser(request);

        Integer pageName = postSelfGetRequest.getPageName();
        Integer pageSize = postSelfGetRequest.getPageSize();

        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);

        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.eq("userId", user.getId());

        Page<Post> postPage = postService.page(new Page<>(pageName, pageSize), postQueryWrapper);

        return ResultUtils.success(postPage);
    }

    @Override
    public BaseResponse<Boolean> updatePost(PostUpdateRequest postUpdateRequest, HttpServletRequest request) {

        User user = userService.getLoginUser(request);

        Post oldPost = postService.getById(postUpdateRequest.getId());

        // 判断是否存在
        ThrowUtils.throwIf(oldPost == null, ErrorCode.NOT_FOUND_ERROR);

        // 只能是帖子拥有者或者管理员更新
        ThrowUtils.throwIf(!Objects.equals(user.getId(), oldPost.getUserId()) || !userService.isAdmin(request), ErrorCode.NO_AUTH_ERROR);

        Post post = new Post();
        BeanUtils.copyProperties(postUpdateRequest, post);
        List<String> tags = postUpdateRequest.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        // 参数校验
        postService.validPost(post, false);

        boolean result = postService.updateById(post);
        return ResultUtils.success(result);
    }

    public void validPost(Post post, boolean add) {
        if (post == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = post.getTitle();
        String content = post.getContent();
        String tags = post.getTags();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content, tags), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }
}




