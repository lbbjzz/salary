package com.zsc.salary.service;

import com.zsc.salary.model.dto.ImportDto;
import com.zsc.salary.model.dto.UpdateSalaryDto;
import com.zsc.salary.model.pojo.Salary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsc.salary.model.vo.EmployeeSalaryVO;
import com.zsc.salary.model.vo.SalaryDeptStatVO;


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
     * 生成指定部门指定月份的员工详细工资
     *
     * @param deptId 部门的Id
     * @param time   生成哪个月份的工资
     */
    void generateSalary(Integer deptId, String time);


    /**
     * 分页获取员工工资详细信息
     *
     * @param map 查询条件 pageNo 页数 pageSize 每页显示的数量 deptId 查询的部门ID time 查询的月数
     * @return 员工工资信息
     */
    Map<String, Object> listSalaryVo(Map<String, Object> map);

    /**
     * 全部获取获取员工工资详细信息
     *
     * @param deptId 查询的部门ID
     * @param time 查询的月数
     * @return 员工工资信息
     */
    Map<String, Object> listSalaryVo(Integer deptId, String time);

    /**
     * 获取工资查询报表
     * @param map 查询条件，beginDate开始时间，endDate结束时间，deptName部门名
     * @return 工资报表数据
     */
    Map<String, Object> listSalaryVoDetail(Map<String, Object> map);

    /**
     * 查询所有部门的月度工资统计信息
     *
     * @param month 查询月份
     * @return 月度统计信息List<SalaryDeptStatVO>
     */
    List<SalaryDeptStatVO> getDeptMonthlySalaryStat(String month);

    /**
     * 根据id获取部门月度工资统计信息
     *
     * @param month  查询月份
     * @param deptId 查询的部门id
     * @return 月度统计信息SalaryDeptStatVO
     */
    SalaryDeptStatVO getDeptMonthlySalaryStatById(String month, Integer deptId);

    /**
     * 根据id查询部门的年度度工资统计信息
     *
     * @param year   查询年份
     * @param deptId 查询的部门id
     * @return 年度统计信息SalaryDeptStatVO
     */
    SalaryDeptStatVO getDeptYearlySalaryStatById(String year, Integer deptId);

    /**
     * 查询所有部门的年度工资统计信息
     *
     * @param year 查询年份
     * @return 年度统计信息List<SalaryDeptStatVO>
     */
    List<SalaryDeptStatVO> getDeptYearlySalaryStat(String year);

    /**
     * 获取公司月度工资统计信息
     * @param month 查询月份
     * @return 公司工资统计数据Map
     */
    Map<String, Object> getCompMonthlySalaryStat(String month);

    /**
     * 获取公司年度工资统计信息
     * @param year 查询年份
     * @return 公司工资统计数据Map
     */
    Map<String, Object> getCompYearlySalaryStat(String year);

    /**
     * 查询该部门在今年该月是否发放了工资
     *
     * @param deptId 部门Id
     * @param time   月份
     * @return true表示已经发放工资 false表示未发放工资
     */
    Boolean judgeSendSalary(Integer deptId, String time);


    /**
     * 查询该部门在今年该月是否为暂存
     *
     * @param deptId 部门Id
     * @param time   月份
     * @return true表示已经发放工资 false表示未发放工资
     */
    Boolean judgeIsStorage(Integer deptId, String time);

    /**
     * 编辑员工的暂存工资
     *
     * @param updateSalaryDto 修改员工暂存工资项目
     */
    void updateSalaryStorage(UpdateSalaryDto updateSalaryDto);

    /**
     * 发放工资
     * 更新员工的工资状态
     *
     * @param deptId 部门Id
     * @param time   时间 2020-08
     */
    void sendSalary(Integer deptId, String time);

    /**
     * 分页获取全部员工工资统计信息
     *
     * @param pageNo 分页的当前页
     * @param pageSize 分页的大小
     * @return employeeSalaryVOList员工工资统计数据，total总个数
     */
    Map<String, Object> getEmployeeSalaryStat(Integer pageNo, Integer pageSize);

    /**
     * 查询员工实发工资
     * @param map 查询条件 含分页
     * @return 员工实发工资
     */
    Map<String, Object> listNetPayVo(Map<String, Object> map);
}
