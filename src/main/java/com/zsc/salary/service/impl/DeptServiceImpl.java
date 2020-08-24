package com.zsc.salary.service.impl;

import com.zsc.salary.model.pojo.Dept;
import com.zsc.salary.mapper.DeptMapper;
import com.zsc.salary.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Override
    public void insert(Dept dept) {

    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public int update(Dept depte) {
        return 0;
    }

    @Override
    public Map<String, Object> listDept(Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public Dept findById(Integer id) {
        return null;
    }
}
