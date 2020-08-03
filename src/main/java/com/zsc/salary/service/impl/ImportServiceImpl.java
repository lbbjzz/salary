package com.zsc.salary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.model.pojo.Import;
import com.zsc.salary.mapper.ImportMapper;
import com.zsc.salary.service.ImportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@Service
public class ImportServiceImpl extends ServiceImpl<ImportMapper, Import> implements ImportService {

    @Resource
    private ImportMapper importMapper;

    @Resource
    private Mapper dozerMapper;

    @Resource
    private EmployeeMapper employeeMapper;


    @Override
    public void insertImport(List<UploadData> list) {

        List<UploadData> copyList =new ArrayList<>(list);

        list.stream().filter(uploadData -> {
           Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>()
                   .select("id")
                   .eq("id", uploadData.getEmployeeId()));
           return employee == null;
       }).forEach(copyList::remove);

        copyList.forEach(System.out::println);

    }
}
