package com.zsc.salary.mapper;

import com.zsc.salary.model.pojo.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.SalaryDeptStatVO;
import com.zsc.salary.model.vo.SalaryVo;
import org.apache.ibatis.annotations.Select;

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
     * @param map 查询的条件 部门Id deptId  查询月份 time
     * @return 员工工资详细信息
     */
    List<SalaryVo> listSalaryVo(Map<String, Object> map);

    /**
     * 获取部门月度工资统计信息
     * @param queryMap 查询条件
     * @return 月度统计信息Map
     */
    SalaryDeptStatVO getDeptMonthlySalaryStatById(Map<String, Object> queryMap);

    SalaryDeptStatVO getDeptYearlySalaryStatById(Map<String, Object> queryMap);

    /**
     * 查询该部门在这个月内是否发放了工资
     * @param deptId 部门Id
     * @param time 月份
     * @return 查询到的数据 为0则代表没有发放工资 其他则为发放了工资
     */
    @Select("SELECT COUNT(s.id) FROM salary s,employee e WHERE s.employee_id = e.id " +
            "AND e.dept_id = #{deptId} AND DATE_FORMAT( s.create_time, '%Y-%m' ) = #{time}")
    Integer isSendSalary(Integer deptId, String time);

}
