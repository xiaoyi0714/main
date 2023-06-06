package rjgc.mall.common;


import lombok.Data;

import java.io.Serializable;

/**
 * Json返回结果封装类
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {
    private Boolean flag; //运行成功与否

    private String msg; //信息

    private T data; //数据

    public Result(Boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public Result() {
    }

    public Result(Boolean flag, String msg, T data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }
}
