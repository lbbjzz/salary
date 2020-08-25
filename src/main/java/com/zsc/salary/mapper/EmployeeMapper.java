package com.zsc.salary.mapper;

import com.zsc.salary.model.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.EmployeeVO;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
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
public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 获取员工的基本工资
     *
     * @param employeeId 员工ID
     * @return 员工基本工资
     */
    @Select("SELECT salary FROM employee e,job j WHERE e.job_id = j.id AND e.id = #{employeeId}")
    BigDecimal getEmployeeSalary(Integer employeeId);

    /**
     * 多表查询返回员工的具体信息，可根据查询条件查询
     * @param map 查询条件map集合
     * @return 符合查询条件的EmployeeVO数据
     */
    List<EmployeeVO> listEmployeeVO(Map<String, Object> map);


}
