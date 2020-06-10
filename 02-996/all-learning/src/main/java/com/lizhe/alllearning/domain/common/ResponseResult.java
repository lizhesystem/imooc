package com.lizhe.alllearning.domain.common;

import com.lizhe.alllearning.exception.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 类描述：通过返回结果模型
 *
 * @author LZ on 2020/6/10
 */
@Data
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = -8552649080651925688L;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 编码
     */
    private String code;

    /**
     * 描述信息
     */
    private String message;

    /**
     * 返回结果
     */
    private T result;

    /**
     * 静态成功方法
     */
    public static <T> ResponseResult<T> success(T result) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setSuccess(Boolean.TRUE);
        responseResult.setResult(result);
        return responseResult;
    }

    /**
     * 失败的方法
     */
    public static <T> ResponseResult<T> failure(String code, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setSuccess(Boolean.FALSE);
        responseResult.setCode(code);
        responseResult.setMessage(message);

        return responseResult;
    }

    /**
     * 失败
     *
     * @param codeEnum
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> failure(ErrorCodeEnum codeEnum) {
        return failure(codeEnum.getCode(), codeEnum.getMessage());
    }
}
