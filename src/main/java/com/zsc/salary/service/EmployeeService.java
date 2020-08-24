package com.zsc.salary.service;

import com.zsc.salary.model.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 新增员工信息
     * @param employee 要导入的员工信息
     */
    void insert(Employee employee);

    /**
     * 根据id删除员工信息
     * @param id 要删除的员工id
     * @return 返回删除结果，0为删除失败，1为删除成功
     */
    int deleteById(Integer id);

    /**
     * 更新员工的信息
     * @param employee 要更新的员工信息
     * @return 返回更新结果，0为更新失败，1为更新成功
     */
    int update(Employee employee);

    /**
     * 分页查询员工信息
     * @param pageNo 分页的当前页数
     * @param pageSize 分页的大小
     * @return 返回List<Employee>数据和total总个数
     */
    Map<String, Object> listEmployee(Integer pageNo, Integer pageSize);

    /**
     * 根据员工id查询单个员工信息
     * @param id 要查询的员工id
     * @return Employee员工数据
     */
    Employee findById(Integer id);
}
