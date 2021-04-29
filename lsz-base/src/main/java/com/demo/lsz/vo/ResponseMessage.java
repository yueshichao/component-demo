package com.demo.lsz.vo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseMessage<T> {

    private static int ERR_CODE = 500;
    private static int OK_CODE = 200;

    private int status;

    private T result;

    private long timestamp;

    public static ResponseMessage ok() {
        ResponseMessage response = new ResponseMessage(OK_CODE, null, new Date().getTime());
        return response;
    }

    public static <T> ResponseMessage<T> ok(T result) {
        ResponseMessage<T> response = new ResponseMessage<>(OK_CODE, result, new Date().getTime());
        return response;
    }

    public static ResponseMessage err() {
        ResponseMessage response = new ResponseMessage(ERR_CODE, null, new Date().getTime());
        return response;
    }

    public static <T> ResponseMessage<T> err(T result) {
        ResponseMessage<T> response = new ResponseMessage<>(ERR_CODE, result, new Date().getTime());
        return response;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
