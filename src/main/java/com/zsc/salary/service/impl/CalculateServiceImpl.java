package com.zsc.salary.service.impl;

import com.zsc.salary.model.pojo.Calculate;
import com.zsc.salary.mapper.CalculateMapper;
import com.zsc.salary.service.CalculateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsc.salary.utils.RedisUtil;
import com.zsc.salary.utils.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@Service
public class CalculateServiceImpl extends ServiceImpl<CalculateMapper, Calculate> implements CalculateService {

    @Resource
    CalculateMapper calculateMapper;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 设置工资计算公式
     * @param calculate
     */
    public Integer UpdateCalculateFormula(Calculate calculate){
        int i = calculateMapper.updateById(calculate);
        return i;
    }
}
