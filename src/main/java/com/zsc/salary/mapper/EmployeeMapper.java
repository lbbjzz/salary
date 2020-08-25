package com.zsc.salary.mapper;

import com.zsc.salary.model.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.EmployeeVO;

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

    List<EmployeeVO> listEmployeeVO(Map<String, Object> map);


}
