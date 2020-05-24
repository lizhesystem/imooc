package com.lizhe.exception.response;

import java.io.Serializable;

/**
 * @author lz
 * @create 2020/5/11
 *
 */
public class IdNotExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String id;

    public IdNotExistException(String id) {
        super("id不存在");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
