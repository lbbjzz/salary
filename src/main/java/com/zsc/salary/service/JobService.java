package com.zsc.salary.service;

import com.zsc.salary.model.dto.JobDto;
import com.zsc.salary.model.pojo.Job;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsc.salary.model.vo.JobCountVO;

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
public interface JobService extends IService<Job> {

    /**
     * 分页获取岗位信息
     *
     *
     * @param pageNo   页号
     * @param pageSize 每页显示的数量
     * 当pageNo, pageSize小于0时，不分页
     * @return 岗位的信息, total 为总数量
     */
    Map<String, Object> listJob(Integer pageNo, Integer pageSize);

    /**
     * 更新岗位信息
     *
     * @param jobDto 岗位信息
     * @return 影响行数
     */
    int updateJob(JobDto jobDto);

    /**
     * 增加岗位信息
     *
     * @param job 岗位信息
     * @return 影响行数
     */
    int insertJob(Job job);

    /**
     * 根据id查询
     * @param id 要查询的职位id
     * @return 查询得到的job数据
     */
    Job findById(Integer id);

    /**
     * 根据id逻辑删除职位
     * @param id 要删除的职位id
     * @return 返回结果，-1为该职位还有员工，0为删除失败，1为删除成功
     */
    int deleteById(Integer id);

    /**
     * 判断岗位名是否重复
     * @param name
     * @return true 表示岗位已存在
     */
    Boolean jobNameExist(String name);

    /**
     * 获取所有岗位的在岗人数
     * @return 在岗人数数据List<JobCountVO>
     */
    List<JobCountVO> getJobCount();
}
