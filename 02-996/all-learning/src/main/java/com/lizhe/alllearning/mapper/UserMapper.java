package com.lizhe.alllearning.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lizhe.alllearning.domain.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper接口
 * @author LZ
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
