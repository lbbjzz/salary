package com.zsc.salary.service;

import com.zsc.salary.model.dto.EmployeeDTO;
import com.zsc.salary.model.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
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
public interface EmployeeService extends IService<Employee> {

    /**
     * 新增员工信息
     *
     * @param employee 要导入的员工信息
     */
    void insert(Employee employee);

    /**
     * 更新员工的信息
     *
     * @param employee 要更新的员工信息
     * @return 返回更新结果，-2为岗位超过核定人数，-1为要修改的部门或岗位不存在，0为更新失败，1为更新成功
     */
    int update(Employee employee);

    /**
     * 多表查询返回员工的具体信息，可根据查询条件查询
     *
     * @param employeeDTO 查询条件DTO
     * @return 返回employeeVO数据和total总个数
     */
    Map<String, Object> listEmployeeVO(EmployeeDTO employeeDTO);

    /**
     * 根据员工id查询单个员工信息
     *
     * @param id 要查询的员工id
     * @return Employee员工数据
     */
    Employee findById(Integer id);

    /**
     * 逻辑删除员工（改变员工状态status）
     * @param id 要删除的员工id
     * @return 删除结果，0为删除失败，1为删除成功
     */
    int deleteById(Integer id);

    /**
     * 获取员工的固定工资
     *
     * @param map 查询条件 含分页
     * @return total 数据的总数 和 数据employeeFixedSalaryVo
     */
    Map<String, Object> listEmployeeFixedSalaryVo(Map<String, Object> map);

    /**
     * 更改用户的采暖补贴
     *
     * @param employeeId     用户Id数组
     * @param heatingSubsidy 采暖补贴 （采暖补贴为0则进行清楚操作）
     */
    void insertHeatingSubsidy(Integer[] employeeId, BigDecimal heatingSubsidy);

    /**
     * 在用户原有基础上增加采暖补贴
     * @param employeeId 用户Id数组
     * @param heatingSubsidy 采暖补贴
     */
    void addHeatingSubsidy(Integer[] employeeId, BigDecimal heatingSubsidy);

    /**
     * 获取全部在职人员的Id
     * @return 在职人员的Id
     */
    List<Employee> getEmployeeId();
}
