package com.lizhe.browser.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author lz
 * @create 2020-05-15
 * 通过实现UserDetailsService接口，实现loadUserByUsername方法来告诉security从如何获取用户信息
 */

@Component
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("登录用户为" + username);
        // 实际项目中调用Dao去数据库查用户名是否存在
        if (!Objects.equals(username, "admin")) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 假设查出来的密码如下
        String pwd = passwordEncoder.encode("123456");
        System.out.println(pwd);

        // 查询到用户后一般将相关信息封装一个UserDetails实例返回给security,ruoyi里返回的是一个登录用户权限类，里面封装的登录相关的对象
        // 还有权限之类的信息，需要时UserDetails的实现类哦

        // 这里测试返回一个security提供的一个实现，第三个参数需要传一个权限集合，这里使用了一个security提供的工具类将用分号分隔的权限字符串转成权限集合，本来应该从用户权限表查询的
        return new SocialUser("admin", pwd, AuthorityUtils.commaSeparatedStringToAuthorityList("user,admin"));
    }
}
