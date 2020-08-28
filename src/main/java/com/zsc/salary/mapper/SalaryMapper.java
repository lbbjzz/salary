package com.zsc.salary.mapper;

import com.zsc.salary.model.pojo.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.SalaryDeptStatVO;
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

    /**
     * 获取部门月度工资统计信息
     * @param queryMap 查询条件
     * @return 月度统计信息Map
     */
    SalaryDeptStatVO getDeptMonthlySalaryStatById(Map<String, Object> queryMap);

    SalaryDeptStatVO getDeptYearlySalaryStatById(Map<String, Object> queryMap);

}
