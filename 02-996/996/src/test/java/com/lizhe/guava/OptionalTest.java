package com.lizhe.guava;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.Optional;

/**
 * @author lz
 * @create 2020/6/2
 */
public class OptionalTest {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private Integer age;
    }

    /**
     * 三种创建optional的方法
     */
    @Test
    public void test() {

        /**
         *  创建一个空的optional对象
         */
        Optional<Object> empty = Optional.empty();

        /**
         *  依据非空的对象，创建一个Optional对象
         */
        Optional<User> user = Optional.of(new User());


        User myUser = new User();

        /**
         *  ofNullable(T value)  依据允许为空的对象，创建一个Optional对象
         */
        String aNull = Optional.of(new User()).map(User::getName).orElse("null");
        System.out.println(aNull);

        boolean present = Optional.ofNullable(new User().getName()).isPresent();
        System.out.println(present);

        String aNull1 = Optional.of(myUser).map(User::getName).map(String::toUpperCase).orElse(null);
        System.out.println(aNull1);

        //Optional<String> s = Optional.of(myUser).flatMap(a -> Optional.of(a.getName()));
        //System.out.println(s);


    }
}
