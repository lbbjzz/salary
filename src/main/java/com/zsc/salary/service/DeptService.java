package com.zsc.salary.service;

import com.zsc.salary.model.pojo.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface DeptService extends IService<Dept> {

    /**
     * 新增部门信息
     *
     * @param dept 要新增的部门信息
     */
    void insert(Dept dept);

    /**
     * 根据id删除部门信息
     *
     * @param id 要删除的部门id
     * @return 返回删除结果，-1为还存在员工，0为删除失败，1为删除成功
     */
    int deleteById(Integer id);

    /**
     * 更新部门信息
     *
     * @param dept 要更新的部门信息
     * @return 返回更新结果，0为更新失败，1为更新成功
     */
    int update(Dept dept);

    /**
     * 分页查询部门信息
     *
     * @param pageNo   分页的当前页数
     * @param pageSize 分页的大小
     *                 当pageNo, pageSize小于0时，不分页
     * @return 返回List<Dept>数据和total总个数
     */
    Map<String, Object> listDept(Integer pageNo, Integer pageSize);

    /**
     * 根据id查询部门信息
     *
     * @param id 要查询的部门id
     * @return Dept部门信息
     */
    Dept findById(Integer id);

    /**
     * 获取全部的部门信息
     *
     * @return 全部的部门信息
     */
    List<Dept> allDept();
}
