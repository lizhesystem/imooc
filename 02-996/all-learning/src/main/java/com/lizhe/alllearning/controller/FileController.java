package com.lizhe.alllearning.controller;

import com.lizhe.alllearning.domain.common.ResponseResult;
import com.lizhe.alllearning.exception.BusinessException;
import com.lizhe.alllearning.exception.ErrorCodeEnum;
import com.lizhe.alllearning.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 类描述：文件服务controller
 *
 * @author LZ on 2020/6/13
 */
@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileController {

    /**
     * @Autowired是默认按照类型装配的 @Resource默认是按照名称装配的
     */
    @Resource(name = "localFileServiceImpl")
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseResult<String> upload(@NotNull MultipartFile file) {

        // 文件上传
        try {
            fileService.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (Exception e) {
            log.error("文件上传失败！");
            throw new BusinessException(ErrorCodeEnum.FILE_UPLOAD_FAILURE, e);
        }

        return ResponseResult.success(file.getOriginalFilename());
    }

}
