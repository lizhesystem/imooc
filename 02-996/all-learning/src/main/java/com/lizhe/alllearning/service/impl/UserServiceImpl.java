package com.lizhe.alllearning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lizhe.alllearning.domain.common.PageQuery;
import com.lizhe.alllearning.domain.common.PageResult;
import com.lizhe.alllearning.domain.dto.UserDTO;
import com.lizhe.alllearning.domain.dto.UserQueryDTO;
import com.lizhe.alllearning.domain.entity.UserDO;
import com.lizhe.alllearning.mapper.UserMapper;
import com.lizhe.alllearning.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类描述：用户服务实现类
 *
 * @author LZ on 2020/6/10
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        // TODO 浅拷贝  属性名相同才能拷贝
        BeanUtils.copyProperties(userDTO, userDO);
        return userMapper.insert(userDO);
    }

    @Override
    public int update(Long id, UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        // 再次赋值 确保更新的正确
        userDO.setId(id);
        return userMapper.updateById(userDO);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public PageResult<List<UserDTO>> query(PageQuery<UserQueryDTO> pageQuery) {

        Page page = new Page(pageQuery.getPageNo(), pageQuery.getPageSize());
        UserDO query = new UserDO();
        // TODO 如果属性不一致，需要做特殊处理 比如在pageQueryDTO里有时间范围之类的参数的话
        BeanUtils.copyProperties(pageQuery.getQuery(), query);

        QueryWrapper queryWrapper = new QueryWrapper(query);

        // 查询得到视图对象
        IPage<UserDO> userDOIPage = userMapper.selectPage(page, queryWrapper);

        // 结果解析 (自定定义自己的,将来不用mybatisPlus了。只动转换部分即可)
        PageResult pageResult = new PageResult();
        pageResult.setPageNo((int) userDOIPage.getCurrent());
        pageResult.setPageSize((int) userDOIPage.getSize());
        pageResult.setTotal(userDOIPage.getTotal());
        pageResult.setPageNum(userDOIPage.getPages());

        // 数据转换
        List<UserDTO> userDTOList = Optional.ofNullable(userDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                // ↑ 防空
                .map(userDO -> {
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(userDO, userDTO);
                    return userDTO;
                }).collect(Collectors.toList());

        pageResult.setData(userDTOList);

        return pageResult;
    }
}
