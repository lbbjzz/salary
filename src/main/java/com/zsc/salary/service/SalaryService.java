package com.zsc.salary.service;

import com.zsc.salary.model.pojo.Salary;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
