package com.zsc.salary.service;

import com.zsc.salary.model.pojo.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
public interface DeptService extends IService<Dept> {

    void insert(Dept dept);

    int deleteById(Integer id);

    int update(Dept depte);

    Map<String, Object> listDept(Integer pageNo, Integer pageSize);

    Dept findById(Integer id);
}
