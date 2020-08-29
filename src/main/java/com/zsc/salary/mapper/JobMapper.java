package com.zsc.salary.mapper;

import com.zsc.salary.model.pojo.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.JobCountVO;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
public interface JobMapper extends BaseMapper<Job> {

    /**
     * 根据id获取岗位的在岗人数
     * @param jobId 要查询的岗位id
     * @return 在岗人数统计实体类JobCountVO
     */
    JobCountVO getJobCountById(Integer jobId);
}
