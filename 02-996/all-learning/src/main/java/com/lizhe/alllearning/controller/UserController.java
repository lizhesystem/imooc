package com.lizhe.alllearning.controller;

import com.lizhe.alllearning.domain.common.PageQuery;
import com.lizhe.alllearning.domain.common.PageResult;
import com.lizhe.alllearning.domain.common.ResponseResult;
import com.lizhe.alllearning.domain.dto.UserDTO;
import com.lizhe.alllearning.domain.dto.UserQueryDTO;
import com.lizhe.alllearning.domain.vo.UserVO;
import com.lizhe.alllearning.exception.ErrorCodeEnum;
import com.lizhe.alllearning.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户Controller
 *
 * @author LZ on 2020/6/10
 */

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {


    @Autowired
    private IUserService userService;

    /**
     * POST /api/users UserDTO
     * 新建用户
     *
     * @return ResponseResult
     */
    @PostMapping
    public ResponseResult save(@RequestBody UserDTO userDTO) {
        int save = userService.save(userDTO);
        if (save == 1) {
            return ResponseResult.success("新增成功!");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
        }
    }

    /**
     * PUT /api/users/{id} UserDTO userDTO
     * 更新用户信息：传id和实体
     *
     * @return ResponseResult
     */
    @PutMapping("/{id}")
    public ResponseResult update(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        int update = userService.update(id, userDTO);
        if (update == 1) {
            return ResponseResult.success("更新成功！");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    /**
     * DELETE /api/users/{id}
     * 删除用户信息
     *
     * @return ResponseResult
     */
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        int delete = userService.delete(id);
        if (delete == 1) {
            return ResponseResult.success("删除成功！");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.DELETE_FAILURE);
        }
    }


    /**
     * GET
     * 查询用户信息
     *
     * @return
     */
    @GetMapping
    public ResponseResult<PageResult> query(Integer pageNo, Integer pageSize, UserQueryDTO query) {
        // 获取分页 构建查询条件
        PageQuery<UserQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);


        // 查询 得到分页实体
        PageResult<List<UserDTO>> pageResult = userService.query(pageQuery);

        // 处理数据 : 实体转换
        List<UserVO> userVOList = Optional.ofNullable(pageResult.getData())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(userDTO -> {
                    // 数据转换 对特殊字段进行处理 返回VO
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(userDTO, userVO);
                    userVO.setPassword("*********");
                    if (!StringUtils.isEmpty(userDTO.getPhone())) {
                        userVO.setPhone(userDTO.getPhone()
                                .replaceAll("(\\d{3})\\d{4}(\\d{4})"
                                        , "$1****$2"));
                    }
                    return userVO;
                }).collect(Collectors.toList());

        // 封装返回结果
        PageResult<List<UserVO>> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);
        result.setData(userVOList);

        return ResponseResult.success(result);
    }
}
