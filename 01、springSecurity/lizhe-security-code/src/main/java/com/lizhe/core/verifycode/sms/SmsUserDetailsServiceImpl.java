package com.lizhe.core.verifycode.sms;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author lz
 * @create 2020/5/19
 */

public class SmsUserDetailsServiceImpl implements UserDetailsService {

    /**
     * 根据登录名查询用户，这里登录名是手机号
     *
     * @param phoneNumber
     * @return
     * @throws PhoneNumberNotFoundException：自定义异常
     */
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws PhoneNumberNotFoundException {
        // 这里实际上应该调用DAO根据手机号查询用户
        // 未查到
        if (!Objects.equals(phoneNumber, "12345678912")) {
            throw new PhoneNumberNotFoundException();
        }
        // 查到了
        // 使用security提供的UserDetails的实现模拟查出来的用户，在你的项目中可以使用User实体类实现UserDetails接口，这样就可以直接返回查出的User实体对象
        return new User("anwen", "123456", AuthorityUtils.createAuthorityList("admin", "super_admin"));
    }
}
