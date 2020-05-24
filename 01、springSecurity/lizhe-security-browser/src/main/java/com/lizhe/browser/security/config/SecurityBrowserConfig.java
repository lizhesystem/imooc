package com.lizhe.browser.security.config;

import com.lizhe.browser.security.handle.CustomAuthenticationFailureHandler;
import com.lizhe.browser.security.handle.CustomAuthenticationSuccessHandler;
import com.lizhe.core.SecurityConstants;
import com.lizhe.core.properties.SecurityProperties;
import com.lizhe.core.verifycode.config.SmsLoginConfig;
import com.lizhe.core.verifycode.config.VerifyCodeValidatorConfig;
import com.lizhe.core.verifycode.filter.SmsCodeAuthenticationFilter;
import com.lizhe.core.verifycode.filter.VerifyCodeAuthenticationFilter;
import com.lizhe.core.verifycode.filter.VerifyCodeValidateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author lz
 * @create 2020-05-15
 * Security 配置类
 */
@Configuration
public class SecurityBrowserConfig extends WebSecurityConfigurerAdapter {


    /**
     * 注入配置类
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 登录成功后的自定义方法
     */
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    /**
     * 登录失败后的自定义方法
     */
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * 自定义验证-验证码的过滤器
     */
    //@Autowired
    //private VerifyCodeAuthenticationFilter verifyCodeAuthenticationFilter;

    /**
     * 自定义短信-验证码的过滤器
     */
    @Autowired
    private SmsCodeAuthenticationFilter smsCodeAuthenticationFilter;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private SmsLoginConfig smsLoginConfig;

    @Autowired
    private VerifyCodeValidateFilter verifyCodeValidateFilter;

    @Autowired
    private VerifyCodeValidatorConfig verifyCodeValidatorConfig;


    /**
     * 在formLogin()后使用loginPage()就能指定登录的页面，同时要记得将该URL的拦截放开；
     * UsernamePasswordAuthenticationFilter默认拦截提交到/login的POST请求并获取登录信息，
     * 如果你想表单填写的action不为/login，那么可以配置loginProcessingUrl使UsernamePasswordAuthenticationFilter与之对应
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 启用验证码校验过滤器
        http.apply(verifyCodeValidatorConfig);
        // 启用短信登录过滤器
        http.apply(smsLoginConfig);
        http
                // 自定义短信验证码过滤器，处理校验短信验证码
                //.addFilterBefore(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 自定义过滤器，验证验证码的，放在usernamePassword前面
                //.addFilterBefore(verifyCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(verifyCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                //设置认证方式为表单登录，若未登录而访问受保护的URL则跳转到表单登录页（security帮我们写了一个默认的登录页）
                .formLogin()
                // 因为是REST所以需要把登录重定向到一个controller里来处理,判断用户是在浏览器访问页面时跳转过来的还是非浏览器如安卓访问REST服务时跳转过来
                .loginPage(SecurityConstants.FORWARD_TO_LOGIN_PAGE_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_FORM_LOGIN_URL)
                // 登录成功的处理和 失败的自定义处理
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                //.httpBasic()
                // 添加其他配置
                .and()
                // 如果勾选记住我的话，会自动往数据库会增加一条session信息。重启服务端发现可以直接访问
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                // 在我们设置的tokenValiditySeconds期间，若用户未登录但从同一浏览器访问受保护服务，RememberMeAuthenticationFilter会拦截到请求：
                .tokenValiditySeconds(3600)
                .userDetailsService(customUserDetailsService)
                // 可配置页面选框的name属性
                //.rememberMeParameter()
                .and()
                // 验证方式配置结束,开始配置验证规则
                .authorizeRequests()
                // 不需要拦截的登录页面
                .antMatchers(
                        SecurityConstants.FORWARD_TO_LOGIN_PAGE_URL,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.VERIFY_CODE_SEND_URL).permitAll()
                // 剩下的设置任何请求都需要认证
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    /**
     * 我们在插入用户到数据库时，需要调用encode对明文密码加密后再插入；在用户登录时，
     * security会调用matches将我们从数据库查出的密文面和用户提交的明文密码进行比对。
     * security为我们提供了一个该接口的非对称加密（对同一明文密码，每次调用encode得到的密文都是不一样的，只有通过matches来比对明文和密文是否对应）
     * 实现类BCryptPasswordEncoder，我们只需配置一个该类的Bean，
     * security就会认为我们返回的UserDetails的getPassword返回的密码是通过该Bean加密过的（所以在插入用户时要注意调用该Bean的encode对密码加密一下在插入数据库）
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
