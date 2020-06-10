package com.lizhe.alllearning.service.impl;

import com.lizhe.alllearning.domain.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 类描述：userService测试类
 *
 * @author LZ on 2020/6/10
 */
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void saveTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username1");
        userDTO.setPassword("password1");
        userDTO.setEmail("email@email.com");
        userDTO.setAge(1);
        userDTO.setPhone("15011110000");
        userDTO.setVersion(1L);

        int save = userService.save(userDTO);

        log.info("{}", save);
    }


    /**
     * 修改测试
     * 乐观锁使用的规则
     * 1. 如果更新数据中不带有version字段：不使用乐观锁，并且version不会累加，更新会成功
     * 2. 如果更新字段中带有version，但与数据库中不一致，更新失败，你的version值要与数据库一致才可以更新成功。
     * 3. 如果带有version，并且与数据库中一致，更新成功，并且version会累加。
     */
    @Test
    public void updateTest() {

        Long id = 1270727132550676481L;
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password666");
        userDTO.setAge(1100);
        //userDTO.setVersion(2L);

        userService.update(id, userDTO);
    }

    @Test
    public void deleteTest() {
        Long id = 1270727132550676481L;
        int delete = userService.delete(id);
        log.info("{}", delete + ":删除成功");
    }

}
