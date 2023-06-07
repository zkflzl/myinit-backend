package com.zkflzl.myinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.common.ResultUtils;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.exception.ThrowUtils;
import com.zkflzl.myinit.mapper.PostMsgMapper;
import com.zkflzl.myinit.model.dto.post.PostMsgUpdateRequest;
import com.zkflzl.myinit.model.entity.Post;
import com.zkflzl.myinit.model.entity.PostMsg;
import com.zkflzl.myinit.model.entity.User;
import com.zkflzl.myinit.service.PostMsgService;
import com.zkflzl.myinit.service.PostService;
import com.zkflzl.myinit.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 */
@Service
public class PostMsgServiceImpl extends ServiceImpl<PostMsgMapper, PostMsg> implements PostMsgService {

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;


    /**
     * 更新拇指味精
     *
     * @param postMsgUpdateRequest 味精后更新请求
     * @param request              请求
     * @return {@link BaseResponse}<{@link Long}>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<Long> updatePostMsg(PostMsgUpdateRequest postMsgUpdateRequest, HttpServletRequest request) {

        Long postId = postMsgUpdateRequest.getPostId();

        User loginUser = userService.getLoginUser(request);

        String tempName = "";

        Integer type = postMsgUpdateRequest.getType();

        tempName = type == 1 ? "thumbNum" : "favourNum";


        // 判断是否存在
        Post post = postService.getById(postId);
        // 帖子是否存在
        ThrowUtils.throwIf(post == null, ErrorCode.NOT_FOUND_ERROR);

        // 获取用户id
        long userId = loginUser.getId();
        // 每个用户串行帖子点赞/收藏
        // 锁必须要包裹住事务方法
        synchronized (String.valueOf(userId).intern()) {

            QueryWrapper<PostMsg> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("userId", userId)
                    .eq("postId", postId);

            PostMsg postMsg = this.getOne(queryWrapper);

            // 暂未出现帖子信息，添加一条
            if (postMsg == null) {
                postMsg = new PostMsg();
                postMsg.setUserId(userId);
                postMsg.setPostId(postId);
                postMsg.setIsThumb(0);
                postMsg.setIsFavour(0);
                postMsg.setCreateTime(new Date());
                postMsg.setUpdateTime(new Date());

                // 添加数据
                boolean result = this.save(postMsg);

                // 添加失败
                ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
            }

            // 是否点赞/收藏
            int tempNum = type == 1 ? postMsg.getIsThumb() : postMsg.getIsFavour();

            // 已点赞/收藏
            if (tempNum == 1) {

                if (type == 1) {
                    postMsg.setIsThumb(0);
                } else {
                    postMsg.setIsFavour(0);
                }

                boolean result = this.update(postMsg, queryWrapper);

                // 更新成功
                if (result) {
                    // 帖子点赞/收藏数 - 1
                    result = postService.update()
                            .eq("id", postId)
                            .gt(tempName, 0)
                            .setSql(tempName + "=" + tempName + "-1")
                            .update();
                }
                if (!result) {
                    // 失败抛异常
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }

                // 完成取消点赞/收藏
                return ResultUtils.success(0L);

            } else {
                // 未点赞/收藏
                if (type == 1) {
                    postMsg.setIsThumb(1);
                } else {
                    postMsg.setIsFavour(1);
                }

                boolean result = this.update(postMsg, queryWrapper);

                // 更新成功
                if (result) {
                    // 帖子点赞/收藏数 + 1
                    result = postService.update()
                            .eq("id", postId)
                            .setSql(tempName + "=" + tempName + "+1")
                            .update();
                }
                // 更新失败
                if (!result) {
                    // 失败抛异常
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }

                // 完成点赞/收藏
                return ResultUtils.success(1L);
            }
        }
    }

    @Override
    public BaseResponse<Long> addForward(Long postId, HttpServletRequest request) {

        Long userId = userService.getLoginUser(request).getId();

        synchronized (String.valueOf(userId).intern()) {
            UpdateWrapper<Post> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", postId)
                    .setSql("forward = forward + 1");
            boolean result = postService.update(updateWrapper);

            // 失败抛异常
            ThrowUtils.throwIf(!result, ErrorCode.SYSTEM_ERROR);

            QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
            postQueryWrapper
                    .select("forward")
                    .eq("id", postId);

            Long forward = Long.valueOf(postService.getOne(postQueryWrapper).getForward());

            return ResultUtils.success(forward);
        }
    }
}




