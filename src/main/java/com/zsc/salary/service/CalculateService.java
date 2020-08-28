package com.zsc.salary.service;

import com.zsc.salary.model.dto.CalculateDto;
import com.zsc.salary.model.pojo.Calculate;
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
public interface CalculateService extends IService<Calculate> {

    /**
     * 增加员工绑定的项目
     *
     * @param employeeId 员工Id
     */
    public void insertCalculate(Integer employeeId);

    /**
     * 返回员工的计算项目
     *
     * @param map 查询条件（暂未写
     * @return total 分页的全部数据 calculateVoList 获取的数据
     */
    Map<String, Object> listCalculateVo(Map<String, Object> map);

    /**
     * 更新员工计算项目
     *
     * @param calculateDto 封装员工计算项目的数据
     */
    void updateCalculate(CalculateDto calculateDto);
}
