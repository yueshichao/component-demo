package com.demo.lsz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
* 视频会议
*
* 调度微服务
* 设备微服务
* 消息微服务
* 权限微服务
*
*
* */
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class DeviceMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceMainApplication.class, args);
    }

}
/*
* 设备微服务：
*   加设备：导入，审核，分配国标ID，kafka推送
*   配置设备能力：音频、视频
*   监听GPS、告警消息
*
* 调度微服务：
*   开启会议
*   结束会议
*   加入会议
*   会议设置
*   会议保存
*
* 消息微服务：
*   LVS负载均衡 -> 接收客户端（系统集成、WEB组件、PC应用、Android应用）消息（WS，HTTP）
*   消息处理 -> 缓存
*   推送消息 -> Android、短信、企业微信、邮件
*
*
*
*
* */