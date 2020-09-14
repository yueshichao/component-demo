package com.demo.lsz.controller;

import com.alibaba.fastjson.JSON;
import com.demo.lsz.dao.CarDao;
import com.demo.lsz.entity.Car;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.ResponseMessage;

import java.util.List;

@RestController
@RequestMapping("/es")
@Slf4j
public class EsDemoController {

    @Autowired
    CarDao carDao;

    @ApiOperation("获取所有数据")
    @GetMapping("/all")
    public String findAll() {
        List<Car> all = carDao.findAll();
        return JSON.toJSONString(all, true);
    }



}
