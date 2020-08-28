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
     * @param pageNo   页号
     * @param pageSize 每页的数量
     * @return 员工工资信息
     */
    Map<String, Object> listSalaryVo(Integer pageNo, Integer pageSize);

    /**
     * 查询所有部门的月度工资统计信息
     * @param month 查询月份
     * @return 月度统计信息List<SalaryDeptStatVO>
     */
    List<SalaryDeptStatVO> getMonthlySalaryStat(String month);

    /**
     * 根据id获取部门月度工资统计信息
     * @param month 查询月份
     * @param deptId 部门id
     * @return 月度统计信息SalaryDeptStatVO
     */
    SalaryDeptStatVO getMonthlySalaryStatByDeptId(String month, Integer deptId);

    SalaryDeptStatVO getYearlySalaryStatByDeptId(String year, Integer deptId);

    List<SalaryDeptStatVO> getYearlySalaryStat(String year);
}
