package com.example.demo.dao.mapper;

import com.example.demo.dao.MyMapper;
import com.example.demo.model.Seckill;
import org.apache.ibatis.annotations.Update;


public interface SeckillMapper extends MyMapper<Seckill> {

    @Update("UPDATE seckill SET number = number - 1 WHERE seckill_id = #{seckillId}")
    int updateNumber(long seckillId);


}
