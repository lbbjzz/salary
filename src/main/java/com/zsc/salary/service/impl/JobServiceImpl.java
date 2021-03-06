package com.zsc.salary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.salary.model.dto.EmployeeDTO;
import com.zsc.salary.model.dto.JobDto;
import com.zsc.salary.model.pojo.Job;
import com.zsc.salary.mapper.JobMapper;
import com.zsc.salary.model.vo.EmployeeVO;
import com.zsc.salary.model.vo.JobCountVO;
import com.zsc.salary.service.EmployeeService;
import com.zsc.salary.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private JobMapper jobMapper;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private Mapper dozerMapper;

    @Override
    public Map<String, Object> listJob(Integer pageNo, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(2);

        if (pageNo >= 0 && pageSize >= 0) {
            PageHelper.startPage(pageNo, pageSize);
        }
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

    @Override
    public Boolean jobNameExist(String name) {
        Job job = jobMapper.selectOne(new QueryWrapper<Job>().eq("name", name));
        return job != null;
    }

    @Override
    public List<JobCountVO> getJobCount() {
        //获取存在的全部岗位id
        List<Job> jobList = jobMapper.selectList(new QueryWrapper<Job>().select("id").eq("deleted", 0));
        List<JobCountVO> jobCountList = new ArrayList<>();
        //遍历岗位id列表
        jobList.forEach(job -> {
            //根据岗位id获取岗位的在岗人数
            JobCountVO jobCountById = jobMapper.getJobCountById(job.getId());
            jobCountList.add(jobCountById);
        });
        return jobCountList;
    }
}
