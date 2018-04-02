package com.example.demo.service;

import com.example.demo.dao.mapper.SeckillMapper;
import com.example.demo.dao.mapper.SuccessKilledMapper;
import com.example.demo.model.QueueEntity;
import com.example.demo.model.SecKillResult;
import com.example.demo.model.Seckill;
import com.example.demo.model.SuccessKilled;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SecKillService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SuccessKilledMapper successKilledMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public List<Seckill> getAllSecKill() {
        return seckillMapper.selectAll();
    }

    public int addSecKill(Seckill seckill) {
        String name = seckill.getName();
        int number = seckill.getNumber();
        return seckillMapper.add(name, number);
    }


    public SecKillResult secKillProduct(String userId, long productId) {
        String key = userId + "_" + productId;
        String state = redisTemplate.opsForValue().get(key);
        // 用户信息加载
        if (null == state) {
            SuccessKilled successKilled = new SuccessKilled();
            successKilled.setUserPhone(Long.valueOf(userId));
            successKilled.setSeckillId(productId);
            successKilled = successKilledMapper.selectOne(successKilled);
            if (null == successKilled) {
                return new SecKillResult(false, "改用户没有预约");
            } else {
                synchronized (this) {
                    state = redisTemplate.opsForValue().get(key);
                    if (null == state) {
                        redisTemplate.opsForValue().set(key, successKilled.getState().toString(), 300, TimeUnit.SECONDS);
                        state = String.valueOf(successKilled.getState());
                    }

                }
            }
        }
        if (StringUtils.equals(state, "-1")) {
            List values = redisTemplate.opsForHash().values(productId + "");
            if (values.size() == 0) {
                Seckill seckill = seckillMapper.selectByPrimaryKey(productId);
                if (null == seckill) {
                    return new SecKillResult(false, "没有该秒杀商品信息");
                }
                synchronized (this) {
                    if (!redisTemplate.opsForHash().hasKey(productId + "", "number")) {
                        HashMap<String, String> productHash = new HashMap<>(16);
                        productHash.put("number", seckill.getNumber() + "");
                        productHash.put("startTime", seckill.getStartTime().getTime() + "");
                        productHash.put("endTime", seckill.getEndTime().getTime() + "");
                        redisTemplate.opsForHash().putAll(productId + "", productHash);
                        redisTemplate.expire(productId + "", 300, TimeUnit.SECONDS);
                        values = redisTemplate.opsForHash().values(productId + "");
                    }
                }
            }
            if (new Date(Long.valueOf((String) values.get(1))).after(new Date(System.currentTimeMillis()))) {
                return new SecKillResult(false, "抢购还没有开始");
            } else if (new Date(Long.valueOf((String) values.get(2))).before(new Date(System.currentTimeMillis()))) {
                return new SecKillResult(false, "抢购已经结束");
            } else {
                Long userState = redisTemplate.opsForValue().increment(key, -1);
                if (userState == 0){
                    Long number = redisTemplate.opsForHash().increment(productId+"","number",-1);
                    if (number >= 0){
                        QueueEntity queueEntity = new QueueEntity(userId, productId);
                        // ExecutorPool.queue.offer(queueEntity);
                    }else {
                        return new SecKillResult(false, "商品已经抢购完成");
                    }
                }else {
                    redisTemplate.opsForValue().increment(key, -1);
                    return new SecKillResult(false, "您已抢购过该产品");
                }

            }
        } else {
            return new SecKillResult(false, "您已抢购过该产品");
        }
        return null;

    }
}
