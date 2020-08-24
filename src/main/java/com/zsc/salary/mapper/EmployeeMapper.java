package com.zsc.salary.mapper;

import com.zsc.salary.model.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.EmployeeVO;

import java.util.List;

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
     * 返回Employee显示的数据
     * @return List<EmployeeVo>数据
     */
    List<EmployeeVO> listEmployeeVO();
}
