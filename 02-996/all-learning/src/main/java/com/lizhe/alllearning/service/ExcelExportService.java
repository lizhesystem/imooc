package com.lizhe.alllearning.service;

import com.lizhe.alllearning.domain.dto.UserQueryDTO;

/**
 * @author lz
 * @create 2020-06-16
 */
public interface ExcelExportService {

    /**
     * 同步导出服务
     *
     * @param query
     * @param filename
     */
    void export(UserQueryDTO query, String filename);


    /**
     * 异步导出服务
     *
     * @param query
     * @param filename
     */
    void asyncExport(UserQueryDTO query, String filename);
}
