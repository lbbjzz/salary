package com.zsc.salary.mapper;

import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.model.pojo.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.EmployeeSalaryVO;
import com.zsc.salary.model.vo.NetPayVo;
import com.zsc.salary.model.vo.SalaryDeptStatVO;
import com.zsc.salary.model.vo.SalaryVo;
import org.apache.ibatis.annotations.Param;
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
     * 获取工资查询报表
     * @param map 查询条件，beginDate开始时间，endDate结束时间，deptName部门名
     * @return 工资查询报表
     */
    List<SalaryVo> listSalaryVoDetail(Map<String, Object> map);

    /**
     * 获取部门月度工资统计信息
     * @param queryMap 查询条件，queryDate查询月份，deptId查询部门id
     * @return 月度统计信息Map
     */
    SalaryDeptStatVO getDeptMonthlySalaryStatById(Map<String, Object> queryMap);

    /**
     * 获取部门年度工资统计信息
     * @param queryMap 查询条件，queryDate查询年份，deptId查询部门id
     * @return 年度统计信息Map
     */
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

    /**
     * 获取公司月度工资统计信息
     * @param queryMap 查询条件，queryDate查询月份
     * @return 公司工资统计数据Map
     */
    Map<String, Object> getCompMonthlySalaryStat(Map<String, Object> queryMap);

    /**
     * 获取公司年度工资统计信息
     * @param queryMap 查询条件，queryDate查询年份
     * @return 公司工资统计数据Map
     */
    Map<String, Object> getCompYearlySalaryStat(Map<String, Object> queryMap);

    /**
     * 查询该部门在这个月内是否为暂存工资
     * @param deptId 部门Id
     * @param time 月份
     * @return 查询到的数据 为0则代表没有暂存工资
     */
    @Select("SELECT COUNT(s.id) FROM salary s,employee e WHERE s.employee_id = e.id " +
            "AND e.dept_id = #{deptId} AND s.status = 0 AND DATE_FORMAT( s.create_time, '%Y-%m' ) = #{time}")
    Integer judgeIsStorage(Integer deptId, String time);

    /**
     * 批量增加员工工资结算
     * @param salaryList 员工工资结算
     */
    void insertEmployeeSalary(@Param("salaryList") List<Salary> salaryList);

    /**
     * 发放工资
     * @param map employeeList 对象 time 2020-08
     */
    void updateSalaryStatus(Map<String, Object> map);

    /**
     * 获取员工工资统计信息
     * @param employeeId 查询的员工id
     * @return 员工共工资统计数据EmployeeSalaryVO
     */
    EmployeeSalaryVO getEmployeeSalaryStat(Integer employeeId);

    /**
     * 获取员工实发工资
     * @param map 封装查询条件
     * @return 员工实发工资
     */
    List<NetPayVo> listNetPayVo(Map<String, Object> map);
}
