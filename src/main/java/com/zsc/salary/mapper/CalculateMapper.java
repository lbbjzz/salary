package com.zsc.salary.mapper;

import com.zsc.salary.model.dto.CalculateDto;
import com.zsc.salary.model.pojo.Calculate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.CalculateVo;
import org.apache.ibatis.annotations.Param;

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
public interface CalculateMapper extends BaseMapper<Calculate> {

    /**
     * 获取员工的计算工资项目
     * @param map 查询条件
     * @return 计算工资项目
     */
    List<CalculateVo> listCalculateVo(Map<String, Object> map);

    /**
     * 更新员工的计算工资项目
     * @param calculateDto 包含更新的数据
     */
    void updateCalculate(CalculateDto calculateDto);

    /**
     * 获取员工的计算属性
     * @param employeeId 员工Id数组
     * @return 员工计算属性
     */
    List<Calculate> listCalculate(@Param("employeeId") Integer[] employeeId);

}
