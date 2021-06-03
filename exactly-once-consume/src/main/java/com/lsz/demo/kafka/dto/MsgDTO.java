package com.lsz.demo.kafka.dto;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgDTO {

    private String id;

    private String msg;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
