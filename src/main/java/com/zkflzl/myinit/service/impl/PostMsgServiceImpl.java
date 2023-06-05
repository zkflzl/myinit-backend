package com.zkflzl.myinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.common.ResultUtils;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.exception.ThrowUtils;
import com.zkflzl.myinit.model.dto.post.PostMsgUpdateRequest;
import com.zkflzl.myinit.model.entity.Post;
import com.zkflzl.myinit.model.entity.PostMsg;
import com.zkflzl.myinit.model.entity.User;
import com.zkflzl.myinit.service.PostMsgService;
import com.zkflzl.myinit.mapper.PostMsgMapper;
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
     *
     * 帖子点赞、取消点赞
     *
     * 封装了事务、加锁
     *
     * @param postMsgUpdateRequest
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<Long> updatePostMsg(PostMsgUpdateRequest postMsgUpdateRequest, HttpServletRequest request) {

        Long postId = postMsgUpdateRequest.getPostId();

        User loginUser = userService.getLoginUser(request);

        // 判断是否存在
        Post post = postService.getById(postId);
        // 帖子是否存在
        ThrowUtils.throwIf(post == null, ErrorCode.NOT_FOUND_ERROR);

        // 是否已帖子收藏
        long userId = loginUser.getId();
        // 每个用户串行帖子收藏
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


            // 已点赞
            if (postMsg.getIsThumb() == 1) {

                postMsg.setIsThumb(0);

                boolean result = this.update(postMsg, queryWrapper);

                // 更新成功
                if (result) {
                    // 帖子收藏数 - 1
                    result = postService.update()
                            .eq("id", postId)
                            .gt("thumbNum", 0)
                            .setSql("thumbNum = thumbNum - 1")
                            .update();
                }
                if (!result) {
                    // 失败抛异常
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }

                // 完成取消点赞
                return ResultUtils.success(0L);

            } else {
                // 未点赞
                postMsg.setIsThumb(1);

                boolean result = this.update(postMsg, queryWrapper);

                // 更新成功
                if (result) {
                    // 帖子收藏数 + 1
                    result = postService.update()
                            .eq("id", postId)
                            .setSql("thumbNum = thumbNum + 1")
                            .update();
                }
                // 更新失败
                if (!result) {
                    // 失败抛异常
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }

                // 完成点赞
                return ResultUtils.success(1L);
            }
        }
    }
}




