package com.demo.lsz.controller;

import cn.hutool.core.date.DateUtil;
import com.demo.lsz.dao.DeviceDao;
import com.demo.lsz.entity.DeviceEntity;
import com.demo.lsz.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/device")
@Slf4j
public class DeviceController {

    @GetMapping("/test")
    public String demo() {
        return "device test ===> " + DateUtil.now();
    }


    @Autowired
    DeviceDao deviceDao;

    @PostMapping("/add")
    public ResponseMessage<DeviceEntity> add(@RequestBody DeviceEntity entity) {
        DeviceEntity add = deviceDao.add(entity);
        return ResponseMessage.ok(add);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseMessage remove(@PathVariable long id) {
        deviceDao.remove(id);
        return ResponseMessage.ok(id);
    }

    @GetMapping("/get/{id}")
    public ResponseMessage<DeviceEntity> get(@PathVariable long id) {
        DeviceEntity entity = deviceDao.get(id);
        return ResponseMessage.ok(entity);
    }

    @GetMapping("/findAll")
    public ResponseMessage<List<DeviceEntity>> findAll() {

        return ResponseMessage.ok(deviceDao.findAll());
    }

}
