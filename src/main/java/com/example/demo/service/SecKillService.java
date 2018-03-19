package com.example.demo.service;

import com.example.demo.dao.mapper.SeckillMapper;
import com.example.demo.model.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecKillService {

    @Autowired
    private SeckillMapper seckillMapper;

    public List<Seckill> getAllSecKill() {
        return seckillMapper.selectAll();
    }

}
