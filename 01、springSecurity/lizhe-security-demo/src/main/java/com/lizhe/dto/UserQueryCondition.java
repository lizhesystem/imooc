package com.lizhe.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 查询对象
 *
 * @author lz
 */
public class UserQueryCondition {

    // 声明一般视图,返回该视图定义的属性
    public interface UserSimpleView {
    }

    // 继承上面的接口,也可以理解完成视图,继承了上面的视图，返回完整视图。
    public interface UserDetailView extends UserSimpleView {
    }

    @NotBlank
    private String search;


    private String age;

    private String xxx;

    /**
     * springmvc会自动把时间戳自动转换为Date格式的时间。
     * 最好前端传时间戳，后端也只保存时间戳，不专门做转换。
     *
     * @Past代表过去的日期
     */
    @Past(
            message = "生日日期不对"
    )
    private Date birthday;


    @JsonView(UserSimpleView.class)
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @JsonView(UserSimpleView.class)
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getXxx() {
        return xxx;
    }

    @JsonView(UserSimpleView.class)
    public void setXxx(String xxx) {
        this.xxx = xxx;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
