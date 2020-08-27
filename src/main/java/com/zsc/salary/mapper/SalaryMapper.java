package com.zsc.salary.mapper;

import com.zsc.salary.model.pojo.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.SalaryVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
public interface SalaryMapper extends BaseMapper<Salary> {

    /**
     * 获取员工工资详细信息
     *
     * @return 员工工资详细信息
     */
    List<SalaryVo> listSalaryVo();

    Map<String, Object> getMaxSalary(Map<String, Object> queryMap);

    BigDecimal getMinSalary(Map<String, Object> queryMap);

    BigDecimal getAvgSalary(Map<String, Object> queryMap);

}
