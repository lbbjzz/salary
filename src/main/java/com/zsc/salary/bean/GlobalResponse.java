package com.zsc.salary.bean;

import com.zsc.salary.enums.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *封装向客户端发回的响应数据
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/6/5 8:26
 */
@Data
@NoArgsConstructor
public class GlobalResponse {
    /**
     * 响应是否成功
     */
    private Boolean success;
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 成功返回的数据
     */
    private Map<String, Object> data = new HashMap<>();

    public GlobalResponse(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static GlobalResponse success() {
        return new GlobalResponse(ResultCode.SUCCESS.getSuccess()
                ,ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
    }

    public static GlobalResponse failed() {
        return new GlobalResponse(ResultCode.FAILED.getSuccess()
                ,ResultCode.FAILED.getCode(),ResultCode.FAILED.getMessage());
    }

    /**
     * 设置结果，形参为结果枚举
     * @param resultCode 自定义结果
     * @return 自定义类型返回
     */
    public static GlobalResponse setResult(ResultCode resultCode) {
        return new GlobalResponse(resultCode.getSuccess()
                ,resultCode.getCode(),resultCode.getMessage());
    }

    public GlobalResponse data (Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    public GlobalResponse data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 自定义状态信息
     * @param message 返回信息
     * @return 返回自己
     */
    public GlobalResponse message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 自定义状态信息
     * @param code 状态码
     * @return 返回自己
     */
    public GlobalResponse code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 自定义状态信息
     * @param success 成功状态
     * @return 返回自己
     */
    public GlobalResponse success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}
