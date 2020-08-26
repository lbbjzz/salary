package com.zsc.salary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.model.pojo.Import;
import com.zsc.salary.mapper.ImportMapper;
import com.zsc.salary.model.dto.ImportDto;
import com.zsc.salary.model.vo.ImportVo;
import com.zsc.salary.service.ImportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        Long startTime = System.currentTimeMillis();

        List<UploadData> newList;

        newList = list.stream().filter(uploadData -> {
           Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>()
                   .select("id")
                   .eq("id", uploadData.getEmployeeId()));
           return employee != null;
       }).collect(Collectors.toList());

        newList.forEach(System.out::println);

        importMapper.insertImport(newList);

        Long endTime = System.currentTimeMillis();
        log.error("耗费时间:" + (endTime - startTime));

    }

    @Override
    public void insertImportNotCheck(List<UploadData> list) {

        Long startTime = System.currentTimeMillis();

        importMapper.insertImport(list);

        Long endTime = System.currentTimeMillis();
        log.error("耗费时间:" + (endTime - startTime));
    }

    @Override
    public int updateImport(ImportDto importDto) {

        Import imports = dozerMapper.map(importDto, Import.class);
        int update = importMapper.updateById(imports);
        if(update == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int deleteImport(Integer importId) {
        return importMapper.deleteById(importId);
    }

    @Override
    public Map<String, Object> listImportVo(Integer pageNo, Integer pageSize, String time) {

        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("time", time);
        Map<String, Object> map = new HashMap<>(2);
        PageHelper.startPage(pageNo, pageSize);
        List<ImportVo> list = importMapper.listImportVo(queryMap);

        PageInfo<ImportVo> page = new PageInfo<> (list);

        Long total = page.getTotal();

        map.put("listImportVo", list);
        map.put("total", total);
        return map;
    }


}
