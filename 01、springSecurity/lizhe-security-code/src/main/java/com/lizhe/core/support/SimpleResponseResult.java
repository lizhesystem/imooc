package com.lizhe.core.support;

/**
 * @author lz
 * @create 2020-05-15
 * 简单的响应对象
 */
public class SimpleResponseResult {

    public SimpleResponseResult(Object content) {
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
