package com.lizhe.alllearning.exception;

import com.lizhe.alllearning.domain.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类描述：全局异常捕获处理器  能拦截到所有@RestController注解的类
 *
 * @author LZ on 2020/6/11
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 拦截业务类异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResponseResult businessExceptionHandle(BusinessException e) {
        log.error("捕捉到业务类异常：", e);
        return ResponseResult.failure(e.getCode(), e.getMessage());
    }


    /**
     * 拦截运行时异常  统一返回json
     *
     * @param e
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseResult runtimeExceptionHandle(RuntimeException e) {
        log.error("捕捉到运行时异常：", e);

        return ResponseResult.failure(
                ErrorCodeEnum.UNKNOWN_ERROR.getCode(),
                e.getMessage());
    }


    /**
     * 捕捉系统级异常
     *
     * @param th
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public ResponseResult throwableHandle(Throwable th) {
        log.error("捕捉Throwable异常：", th);

        return ResponseResult.failure(
                ErrorCodeEnum.SYSTEM_ERROR.getCode(),
                th.getMessage());
    }

}
