package com.example.demo.controller;

import com.example.demo.model.RespCode;
import com.example.demo.model.RespEntity;
import com.example.demo.model.SecKillResult;
import com.example.demo.model.Seckill;
import com.example.demo.service.SecKillService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    private DateTimeFormatter formatter;

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);


    @RequestMapping(value = "/all")
    public RespEntity getAllSecKill() {
        List<Seckill> seckills = secKillService.getAllSecKill();
        RespEntity respEntity = new RespEntity(RespCode.SUCCESS, seckills);
        return respEntity;
    }

    @RequestMapping(value = "/product/{productId}")
    public RespEntity secKillProduct(@RequestParam("userId") String userId, @PathVariable("productId") long productId) {
        SecKillResult secKillResult = secKillService.secKillProduct(userId, productId);
        return null;
    }

    @RequestMapping(value = "/add", method = POST)
    public RespEntity addSeckill(@RequestBody Map<String, String> seckill) {
        DateTime dt = DateTime.now();
        DateTime now = new DateTime(dt.toString());
        logger.info("DateTime :{}" + now);
        return new RespEntity(RespCode.SUCCESS);
    }

}

