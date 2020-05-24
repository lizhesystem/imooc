package com.lizhe.service;

import org.springframework.stereotype.Service;

/**
 * @author lz
 * @create 2020/5/11
 */
@Service
public class UserService {
    public boolean checkUsernameIfExists(String username) {
        // 校验用户username是"tom"
        return "tom".equals(username);
    }
}
