package com.zsc.salary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.salary.model.dto.EmployeeDTO;
import com.zsc.salary.model.pojo.Dept;
import com.zsc.salary.mapper.DeptMapper;
import com.zsc.salary.model.vo.EmployeeVO;
import com.zsc.salary.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsc.salary.service.EmployeeService;
import com.zsc.salary.utils.RedisUtil;
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
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private RedisUtil redisUtil;

    private final String DEPT_KEY = "dept:";

    @Override
    public void insert(Dept dept) {
        deptMapper.insert(dept);
    }

    @Override
    public int deleteById(Integer id) {
        String key = DEPT_KEY + id;
        EmployeeDTO employeeDTO = new EmployeeDTO(-1, -1, id, null, null);
        Map<String, Object> map = employeeService.listEmployeeVO(employeeDTO);
        int flag;

        if((long)map.get("total") > 0){
            flag = -1;
            return flag;
        }

        if (redisUtil.hasKey(key)) {
            redisUtil.del(key);
        }
        flag = deptMapper.deleteById(id);
        return flag;
    }

    @Override
    public int update(Dept dept) {
        String key = DEPT_KEY + dept.getId();
        int flag = deptMapper.updateById(dept);
        if (redisUtil.hasKey(key)) {
            redisUtil.set(key, dept, 24);
        }
        return flag;
    }

    @Override
    public Map<String, Object> listDept(Integer pageNo, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(2);

        if (pageNo >= 0 && pageSize >= 0){
            PageHelper.startPage(pageNo, pageSize);
        }
        List<Dept> list = deptMapper.selectList(null);
        PageInfo<Dept> pageInfo = new PageInfo<>(list);

        map.put("listDept", list);
        map.put("total", pageInfo.getTotal());
        return map;
    }

    @Override
    public Dept findById(Integer id) {
        String key = DEPT_KEY + id;
        Dept dept;
        if (redisUtil.hasKey(key)) {
            dept = (Dept) redisUtil.get(key);
        } else {
            dept = deptMapper.selectById(id);
            if (dept != null) {
                redisUtil.set(key, dept, 24);
            }
        }
        return dept;
    }
}
