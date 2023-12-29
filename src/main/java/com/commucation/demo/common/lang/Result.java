package com.commucation.demo.common.lang;

import lombok.Data;
import java.io.Serializable;

/**
 * @author DELL
 */
@Data
public class Result implements Serializable {
    private int code;
    // 200正常，非200表示异常
    private String message;
    private Object data;

    public static Result success(Object data) {
        return returnResult(200, "操作成功", data);
    }

    public static Result success(int code, String message, Object data) {
        return returnResult(code, message, data);
    }

    public static Result fail(String message) {
        return returnResult(400, message, null);
    }

    public static Result fail(int code, String message, Object data) {
        return returnResult(code, message, data);
    }

    private static Result returnResult(int code, String message, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
