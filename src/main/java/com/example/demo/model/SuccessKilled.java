package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class SuccessKilled {
    /**
     * 秒杀商品ID
     */
    private Long seckillId;

    /**
     * 用户手机号
     */
    private Long userPhone;

    /**
     * 状态标识:-1:无效 0:成功 1:已付款 2:已发货
     */
    private Byte state;

    /**
     * 创建时间
     */
    private Date createTime;

    public SuccessKilled(Long seckillId, Long userPhone, Byte state) {
        this.seckillId = seckillId;
        this.userPhone = userPhone;
        this.state = state;
    }
}