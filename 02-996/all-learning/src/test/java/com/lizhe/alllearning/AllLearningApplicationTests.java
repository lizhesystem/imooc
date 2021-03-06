package com.lizhe.alllearning;

import com.lizhe.alllearning.domain.entity.UserDO;
import com.lizhe.alllearning.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Slf4j
class AllLearningApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    private void find() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("username", "username1");
        List<UserDO> userDOList = userMapper.selectByMap(param);

        log.info("{}", userDOList);
    }
}
