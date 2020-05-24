package com.lizhe.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.lizhe.annotation.valid.Unrepeatable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author lz
 */
public class User extends UserQueryCondition {


    private String id;

    @NotBlank(
            message = "姓名不能为空"
    )
    @Length(
            message = "姓名最长为20个字符"
            , min = 1
            , max = 20
    )
    @Unrepeatable
    private String username;

    @NotBlank(
            message = "密码不能为空"
    )
    private String password;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
