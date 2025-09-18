package com.ruoyisecurity.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后端统一返回结果 Result.class
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // 忽略空值
public class Result<T> implements Serializable {

    private Integer code; // 编码：1成功，0和其它数字为失败
    private String msg; // 错误信息
    private T data; // 数据

    /**
     * 请求成功，不带任何消息
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        return new Result<>(1, null, null);
    }

    /**
     * 请求成功，带消息和数据
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(String msg, T object) {
        return new Result<>(1, msg, object);
    }

    /**
     * 请求成功,带数据
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T object) {
        return new Result<>(1, null, object);
    }

    /**
     * 请求成功,带消息
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(1, message, null);
    }

    /**
     * 请求错误，带消息提示
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg, null);
    }

    /**
     * 请求错误，带消息提示和状态码
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

}