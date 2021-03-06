package com.zsc.salary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.salary.model.dto.CalculateDto;
import com.zsc.salary.model.pojo.Calculate;
import com.zsc.salary.mapper.CalculateMapper;
import com.zsc.salary.model.vo.CalculateVo;
import com.zsc.salary.service.CalculateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsc.salary.utils.RedisUtil;
import com.zsc.salary.utils.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public void insertCalculate(Integer employeeId) {
        Calculate calculate = new Calculate();
        calculate.setEmployeeId(employeeId);
        while (true) {
            if (calculateMapper.insert(calculate) == 1) {
                break;
            }
        }
    }

    @Override
    public Map<String, Object> listCalculateVo(Map<String, Object> map) {
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");
        PageHelper.startPage(pageNo, pageSize);
        log.error(String.valueOf(map));
        List<CalculateVo> calculateVoList = calculateMapper.listCalculateVo(map);
        log.error(String.valueOf(calculateVoList));
        Map<String, Object> result = new HashMap<>();
        PageInfo<CalculateVo> info = new PageInfo<>(calculateVoList);
        result.put("calculateVoList", calculateVoList);
        result.put("total", info.getTotal());
        return result;
    }

    @Override
    public void updateCalculate(CalculateDto calculateDto) {
        log.error(String.valueOf(calculateDto));
        calculateMapper.updateCalculate(calculateDto);
    }
}
