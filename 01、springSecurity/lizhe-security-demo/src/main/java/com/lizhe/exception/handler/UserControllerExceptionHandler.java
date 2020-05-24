package com.lizhe.exception.handler;

import com.lizhe.exception.response.IdNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lz
 * @create 2020/5/11
 * <p>
 * 定义自定义异常
 * SpringMVC默认只会将异常的message返回，
 * 如果我们需要将IdNotExistException的id也返回以给前端更明确的提示，不要那一堆无关的错误返回，就需要我们自定义异常处理。
 * 1、自定义的异常处理类需要添加@ControllerAdvice,@ControllerAdvice是组件注解，他使得其实现类能够被classpath扫描自动发现
 * 2、在处理异常的方法上使用@ExceptionHandler声明该方法要截获哪些异常，所有的Controller若抛出这些异常中的一个则会转为执行该方法
 * 3、捕获到的异常会作为方法的入参。
 * 4、方法返回的结果与Controller方法返回的结果意义相同，如果需要返回json则需在方法上添加@ResponseBody注解，如果在类上添加该注解则表示每个方法都有该注解
 * <p>
 * <p>
 * 注解@ControllerAdvice的类可以拥有@ExceptionHandler, @InitBinder或 @ModelAttribute注解的方法，
 * 并且这些方法会被应用到控制器类层次的所有@RequestMapping方法上。
 * <p>
 * 看ruoyi使用的是@RestControllerAdvice注解，感觉这个注解类似于 @RestController 与 @Controller的区别
 */

@ControllerAdvice
@ResponseBody
public class UserControllerExceptionHandler {

    @ExceptionHandler({IdNotExistException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //自定义异常状态码
    public Map<String, Object> handlerIdNotExistException(IdNotExistException e) {
        Map<String, Object> jsonResult = new HashMap<String, Object>(0);
        jsonResult.put("message", e.getMessage());
        jsonResult.put("id", e.getId());
        return jsonResult;
    }

    /*
    默认异常：
    {
          "timestamp": 1566270990177,
          "status": 500,
          "error": "Internal Server Error",
          "exception": "top.zhenganwen.securitydemo.exception.response.IdNotExistException",
          "message": "id不存在",
          "path": "/user/1"
    }


    自定义后异常返回结果
    {
         "message": "id不存在",
         "id": "3"
    }
     */
}
