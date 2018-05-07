package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * @author ChengLong
 * @date 2018.1.29
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public RespEntity getAll() {
        List<User> list = userService.getAll();
        RespEntity respEntity = new RespEntity(RespCode.SUCCESS, list);
        return respEntity;
    }

    @GetMapping(value = "/one")
    public RespEntity getOne(@RequestParam(value = "uuid") String userId) {

        User user = userService.getOne(userId);
        RespEntity respEntity = new RespEntity(RespCode.SUCCESS, user);
        return respEntity;
    }

    @PostMapping(value = "/add")
    public RespEntity addOne(@RequestBody Map<String, String> user) {
        User userObj = new User();
        userObj.setSex(user.get("sex"));
        userObj.setNickName(user.get("nick_name"));
        String userId = userService.insert(userObj);
        RespEntity respEntity = new RespEntity(RespCode.SUCCESS, userService.getOne(userId));
        return respEntity;
    }

    @PostMapping(value = "/update")
    public RespEntity updateOne(@RequestBody Map<String, String> user, HttpServletResponse response) {
        RespEntity respEntity = new RespEntity();
        String uuid = user.get("uuid");
        if (StringUtils.isBlank(uuid)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            respEntity.setRespCode(RespCode.WARN);
            return respEntity;
        }

        if (null == userService.getOne(uuid)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            respEntity.setRespCode(RespCode.WARN);
            return respEntity;
        }

        User userObj = User.builder()
                .id(uuid)
                .nickName(user.get("nick_name"))
                .sex(user.get("sex"))
                .build();
        userService.update(userObj);

        respEntity.setData(userObj);
        respEntity.setRespCode(RespCode.SUCCESS);
        respEntity.getMsg();
        return respEntity;
    }

}
