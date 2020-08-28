package com.zsc.salary.service;

import com.zsc.salary.model.pojo.Salary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsc.salary.model.vo.SalaryDeptStatVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
public interface SalaryService extends IService<Salary> {

    /**
     * 生成员工详细工资
     *
     * @param importId 导入的数据
     * @return 影响行数
     */
    int addSalary(Integer importId);


    /**
     * 分页获取员工工资详细信息
     *
     * @param map 查询条件 pageNo 页数 pageSize 每页显示的数量 deptId 查询的部门ID time 查询的月数
     * @return 员工工资信息
     */
    Map<String, Object> listSalaryVo(Map<String, Object> map);

    Map<String, Object> listSalaryVoDetail(Map<String, Object> map);

    /**
     * 查询所有部门的月度工资统计信息
     * @param month 查询月份
     * @return 月度统计信息List<SalaryDeptStatVO>
     */
    List<SalaryDeptStatVO> getDeptMonthlySalaryStat(String month);

    /**
     * 根据id获取部门月度工资统计信息
     * @param month 查询月份
     * @param deptId 查询的部门id
     * @return 月度统计信息SalaryDeptStatVO
     */
    SalaryDeptStatVO getDeptMonthlySalaryStatById(String month, Integer deptId);

    /**
     * 根据id查询部门的年度度工资统计信息
     * @param year 查询年份
     * @param deptId 查询的部门id
     * @return 年度统计信息SalaryDeptStatVO
     */
    SalaryDeptStatVO getDeptYearlySalaryStatById(String year, Integer deptId);

    /**
     * 查询所有部门的年度工资统计信息
     * @param year 查询年份
     * @return 年度统计信息List<SalaryDeptStatVO>
     */
    List<SalaryDeptStatVO> getDeptYearlySalaryStat(String year);

    Map<String, Object> getCompMonthlySalaryStat(String month);

    Map<String, Object> getCompYearlySalaryStat(String year);

    /**
     * 查询该部门在今年该月是否发放了工资
     * @param deptId 部门Id
     * @param time 月份
     * @return true表示已经发放工资 false表示未发放工资
     */
    Boolean judgeSendSalary(Integer deptId, String time);
}
