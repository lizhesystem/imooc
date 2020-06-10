package com.lizhe.alllearning.service;

import com.lizhe.alllearning.domain.common.PageQuery;
import com.lizhe.alllearning.domain.common.PageResult;
import com.lizhe.alllearning.domain.dto.UserDTO;
import com.lizhe.alllearning.domain.dto.UserQueryDTO;

import java.util.List;

/**
 * 类描述：用户服务接口
 *
 * @author LZ on 2020/6/10
 */
public interface IUserService {

    /**
     * 新增
     *
     * @param userDTO
     * @return
     */
    int save(UserDTO userDTO);

    /**
     * 更新
     *
     * @param id
     * @param userDTO
     * @return
     */
    int update(Long id, UserDTO userDTO);

    /**
     * 刪除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     *  分页查询
     * @param pageQuery
     * @return
     */
    PageResult<List<UserDTO>> query(PageQuery<UserQueryDTO> pageQuery);
}
