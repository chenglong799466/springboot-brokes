package com.example.demo.controller;

import com.example.demo.model.RespCode;
import com.example.demo.model.RespEntity;
import com.example.demo.model.Seckill;
import com.example.demo.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    @RequestMapping(value = "/all")
    public RespEntity getAllSecKill() {
        List<Seckill> seckills = secKillService.getAllSecKill();
        RespEntity respEntity = new RespEntity(RespCode.SUCCESS, seckills);
        return respEntity;
    }


}
