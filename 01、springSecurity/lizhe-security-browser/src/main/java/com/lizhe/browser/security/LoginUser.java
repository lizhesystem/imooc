package com.lizhe.browser.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author lz
 * @create 2020-05-15
 */
@Data
public class LoginUser  implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String phone;
    private int deleted;			//0-"正常的"，1-"已删除的"
    private int accountNonLocked;	 //0-"账号未被冻结"，1-"账号已被冻结"


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return deleted == 0;
    }
}
