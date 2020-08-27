package com.zsc.salary.mapper;

import com.zsc.salary.model.pojo.Calculate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.CalculateVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
public interface CalculateMapper extends BaseMapper<Calculate> {

    /**
     * 获取员工的计算工资项目
     * @param map 查询条件
     * @return 计算工资项目
     */
    List<CalculateVo> listCalculateVo(Map<String, Object> map);
}
