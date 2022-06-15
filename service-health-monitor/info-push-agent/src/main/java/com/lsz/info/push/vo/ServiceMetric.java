package com.lsz.info.push.vo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.Data;

@Data
public class ServiceMetric {

    private String app;

    private String uri;

    private String traceId;

    private Long start;

    private Long end;

    // 毫秒
    private Long duration;

    private boolean success;

    public static ServiceMetric defaultMetric() {
        ServiceMetric vo = new ServiceMetric();
        vo.app = "info-push-service";
        vo.uri = "/get/user";
        vo.traceId = IdUtil.fastSimpleUUID();
        vo.start = DateUtil.current();
        vo.duration = RandomUtil.randomLong(10, 3000);
        vo.end = vo.start + vo.duration * 1000;
        vo.success = RandomUtil.randomBoolean();
        return vo;
    }


}
