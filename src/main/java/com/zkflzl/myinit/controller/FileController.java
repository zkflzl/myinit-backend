package com.zkflzl.myinit.controller;

import cn.hutool.core.io.FileUtil;
import com.zkflzl.myinit.common.BaseResponse;
import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.common.ResultUtils;
import com.zkflzl.myinit.exception.BusinessException;
import com.zkflzl.myinit.exception.ThrowUtils;
import com.zkflzl.myinit.model.entity.User;
import com.zkflzl.myinit.model.enums.FileUploadBizEnum;
import com.zkflzl.myinit.service.UserService;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 文件接口
 *
 * @author <a href="https://gitee.com/zkflzl">zkflzl</a>
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private UserService userService;

    @Resource
    private MinioClient minioClient;

    @Value("${minio.backetName}")
    private String bucketName;

    /**
     * 文件上传
     *
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {

        ThrowUtils.throwIf(multipartFile.isEmpty(), ErrorCode.PARAMS_ERROR, "上传文件不能为空");

        User loginUser = userService.getLoginUser(request);
        try {

            // 检查存储桶是否存在，不存在则创建
            boolean found = minioClient.bucketExists(bucketName);
            if (!found) {
                minioClient.makeBucket(bucketName);
            }

            //TODO 更具业务修改
            String filePath = String.format("test/%d", loginUser.getId());

            // 上传
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(multipartFile.getName())
                            .filename(filePath)
                            .build());

        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success("上传成功");
    }

    /**
     * 校验文件
     *
     * @param multipartFile
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }
}
