package com.zsc.salary.service.impl;

import com.zsc.salary.model.pojo.Job;
import com.zsc.salary.mapper.JobMapper;
import com.zsc.salary.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

}
