package com.lizhe.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lizhe.dto.User;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


/**
 * @author lz
 */

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 根据参数获取
     * 参数根据注解来设置,比如接收封装成对象,接收单个参数等。
     *
     * @return
     */
    @GetMapping
    @JsonView(User.UserSimpleView.class)  // 全属性,但是没password
    public List<User> query(@RequestParam(value = "username", defaultValue = "", required = true) String username, User user, Pageable pageable) {
        // ReflectionToStringBuilder反射工具类能够在对象没有重写toString方法时通过反射帮我们查看对象的属性。
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));

        System.out.println("=====分页信息===");
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());


        System.out.println(username);
        return Arrays.asList(new User(), new User(), new User());
    }

    /**
     * 获取详情
     * 可以使用正则,只能数组
     * JsonView注解：全属性 有password 返回:{"search":null,"age":null,"xxx":null,"username":"lz","password":null}
     * <p>
     * <p>
     * 异常处理：
     * SpringMVC默认只会将异常的message返回，
     * 如果我们需要将IdNotExistException的id也返回以给前端更明确的提示，就需要我们自定义异常处理。
     * 1、自定义的异常处理类需要添加@ControllerAdvice
     * 2、在处理异常的方法上使用@ExceptionHandler声明该方法要截获哪些异常，所有的Controller若抛出这些异常中的一个则会转为执行该方法
     * 3、捕获到的异常会作为方法的入参
     * 4、方法返回的结果与Controller方法返回的结果意义相同，如果需要返回json则需在方法上添加@ResponseBody注解，如果在类上添加该注解则表示每个方法都有该注解
     */
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id) {
        //throw new RuntimeException("id不存在");
        // 该异常为自定义异常
        //throw new IdNotExistException(id);
        System.out.println(id);
        User user = new User();
        user.setUsername("lz");
        return user;
    }

    /**
     * 创建
     *
     * @param user
     * @return
     */
    @PostMapping
    public User create(@Valid @RequestBody User user/*, BindingResult errors*/) {

        //if (errors.hasErrors()) {
        //    errors.getAllErrors().stream().forEach(e -> {
        //        System.out.println(e.getDefaultMessage());
        //        // 用户名已经注册，自定义的校验注解已经生效了
        //    });
        //}

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        //Date类型接收时间戳,默认转换成Sun May 10 15:15:52 CST 2020
        System.out.println(user.getBirthday());

        user.setId("1");
        // 返回转换成时间戳返回了
        return user;
    }

    /**
     * 注意：使用@Valid的话接收的对象只能是一个，如果是2个的话，无法验证
     * 因为他不知道是根据哪个对象去校验了。
     *
     * @param user
     * @return
     */
    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user,/* @PathVariable Long id,*/BindingResult errors) {

        // 打印BindingResult错误对象
        // 上述代码中，如果在校验的Bean和BindingResult之间插入了一个id，你会发现BindingResult不起作用了，结果直接返回400错误了。
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(e -> {
                FieldError fieldError = (FieldError) e;
                System.out.println(fieldError.getField() + " " + fieldError.getDefaultMessage());
            });
            // 注释上面的id后， must be in the past --返回200了，并且打印出来错误
            // 测试打印： birthday must be in the past
        }

        //System.out.println(id);

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());

        System.out.println();


        user.setId("1");
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println(id);
    }


    /**
     * 测试登录成功后 security在内存中保存的用户对象
     * SecurityContext其实就是对Authentication的一层包装
     *
     * @return
     */
    @GetMapping("/info1")
    public Object info1() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/info2")
    public Object info2(Authentication authentication) {
        return authentication;
    }

    /**
     *
     *
     如果只想获取Authentication中的UserDetails对应的部分，则可使用@AuthenticationPrinciple注解 UserDetails currentUser
     */
    @GetMapping("/info3")
    public Object info3(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }
}
