package com.zsc.salary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.salary.model.dto.EmployeeDTO;
import com.zsc.salary.model.dto.JobDto;
import com.zsc.salary.model.pojo.Job;
import com.zsc.salary.mapper.JobMapper;
import com.zsc.salary.model.vo.EmployeeVO;
import com.zsc.salary.service.EmployeeService;
import com.zsc.salary.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Resource
    JobMapper jobMapper;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private Mapper dozerMapper;

    @Override
    public Map<String, Object> listJob(Integer pageNo, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(2);
        PageHelper.startPage(pageNo, pageSize);
        List<Job> jobs = jobMapper.selectList(null);
        PageInfo<Job> info = new PageInfo<>(jobs);
        map.put("jobs", jobs);
        map.put("total", info.getTotal());
        return map;
    }

    @Override
    public int updateJob(JobDto jobDto) {
        Job job = dozerMapper.map(jobDto, Job.class);
        return jobMapper.updateById(job);
    }

    @Override
    public int insertJob(Job job) {
        return jobMapper.insert(job);
    }

    @Override
    public Job findById(Integer id) {
        return jobMapper.selectById(id);
    }

    @Override
    public int deleteById(Integer id) {
        int flag;
        EmployeeDTO employeeDTO = new EmployeeDTO(-1, -1, null, id, null);
        Map<String, Object> map = employeeService.listEmployeeVO(employeeDTO);

        if ((long) map.get("total") > 0) {
            flag = -1;
            return flag;
        }

        flag = jobMapper.deleteById(id);
        return flag;
    }
}
