package com.lizhe.core.verifycode.config;

import com.lizhe.core.verifycode.filter.VerifyCodeValidateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author lz
 * @create 2020-05-19
 */
@Component
public class VerifyCodeValidatorConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private VerifyCodeValidateFilter verifyCodeValidateFilter;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterBefore(verifyCodeValidateFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
