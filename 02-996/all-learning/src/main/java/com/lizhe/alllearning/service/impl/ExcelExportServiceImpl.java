package com.lizhe.alllearning.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.lizhe.alllearning.domain.common.PageQuery;
import com.lizhe.alllearning.domain.common.PageResult;
import com.lizhe.alllearning.domain.dto.UserDTO;
import com.lizhe.alllearning.domain.dto.UserExportDTO;
import com.lizhe.alllearning.domain.dto.UserQueryDTO;
import com.lizhe.alllearning.service.ExcelExportService;
import com.lizhe.alllearning.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类描述:
 * excel导出实现类
 *
 * @author Administrator
 * @create 2020-06-16 9:39
 */

@Service
@Slf4j
public class ExcelExportServiceImpl implements ExcelExportService {

    @Resource(name = "localFileServiceImpl")
    private FileService fileService;

    @Autowired
    private UserServiceImpl userService;


    private void export(ByteArrayOutputStream outputStream, UserQueryDTO query) {
        // 导出的实现
        // step1. 需要创建一个EasyExcel导出对象
        ExcelWriter excelWriter = EasyExcelFactory
                .write(outputStream, UserExportDTO.class)
                .build();

        // step1. 分批加载数据
        PageQuery<UserQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setQuery(query);
        // 默认每页2条数据
        pageQuery.setPageSize(2);

        int pageNo = 0;
        PageResult<List<UserDTO>> pageResult;

        do {
            // 先累加 再赋值 要跟pageNo++区分
            pageQuery.setPageNo(++pageNo);

            log.info("开始导出第[ {} ]页数据！", pageNo);

            pageResult = userService.query(pageQuery);

            // 数据转换：UserDTO 转换成 UserExportDTO
            List<UserExportDTO> userExportDTOList = Optional
                    .ofNullable(pageResult.getData())
                    .map(List::stream)
                    .orElseGet(Stream::empty)
                    .map(userDTO -> {
                        UserExportDTO userExportDTO = new UserExportDTO();

                        // 转换
                        BeanUtils.copyProperties(userDTO, userExportDTO);
                        return userExportDTO;
                    })
                    .collect(Collectors.toList());

            // step3. 导出分批加载的数据
            // 将数据写入到不同的sheet页中
            WriteSheet writeSheet = EasyExcelFactory.writerSheet(pageNo,
                    "第" + pageNo + "页").build();
            excelWriter.write(userExportDTOList, writeSheet);

            log.info("结束导出第[ {} ]页数据！", pageNo);

            // 总页数 大于 当前页 说明还有数据，需要再次执行
        } while (pageResult.getPageNum() > pageNo);


        // step4. 收尾，执行finish，才会关闭Excel文件流
        excelWriter.finish();

        log.info("完成导出！");

    }


    @Override
    public void export(UserQueryDTO query, String filename) {

        // 创建字节输出流
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        // step1:实现数据导出Excel中
        export(outputStream, query);

        // 输入流
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        // step2:实现文件上传,导出的excel文件上传到我们的本地upload文件中。
        fileService.upload(inputStream, filename);

    }


    /**
     * 借助@Async注解，使用线程池执行方法
     *
     * @param query
     * @param filename
     */
    @Async("exportServiceExecutor")
    @Override
    public void asyncExport(UserQueryDTO query, String filename) {
        export(query, filename);
    }
}
