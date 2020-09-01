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
import com.zsc.salary.model.vo.CalculateVo;
import com.zsc.salary.model.vo.ImportVo;
import com.zsc.salary.service.ImportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.dozer.Mapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
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

        list.forEach(System.out::println);
        for (UploadData uploadData : list) {
            importMapper.uploadImport(uploadData);
        }
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
    public int clearImportData(Integer importId) {
        Import imports = new Import();
        imports.setId(importId)
                .setBackPay(BigDecimal.valueOf(0))
                .setLateDay(0)
                .setPersonalLeaveDay(0)
                .setSickLeaveDay(0)
                .setOvertimeDay(0);

        return importMapper.updateById(imports);
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

    @Override
    public Map<String, Object> listImportVo(Map<String, Object> map) {
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");
        PageHelper.startPage(pageNo, pageSize);
        log.error(String.valueOf(map));
        List<ImportVo> listImportVo = importMapper.listImport(map);
        log.error(String.valueOf(listImportVo));
        Map<String, Object> result = new HashMap<>(2);
        PageInfo<ImportVo> info = new PageInfo<>(listImportVo);
        result.put("listImportVo", listImportVo);
        result.put("total", info.getTotal());
        return result;
    }

    @Override
    public List<ImportVo> listImportVoRepeatData() {
        return importMapper.listImportVoRepeatData();
    }

    @Override
    public void deleteRepeatImportData(Integer[] id) {
        List<Integer> importId = new ArrayList<> (Arrays.asList(id));
        Map<String, Object> map = new HashMap<>(1);
        map.put("importId", importId);
        importMapper.deleteImportRepeat(map);
    }

    @Scheduled(cron = "0 0 0 1 * ? ")
    @Override
    public void monthImportData() {
        List<Employee> employeeList = employeeMapper.selectList(new QueryWrapper<Employee>().eq("status", 1).select("id"));
        Import importing = new Import();
        employeeList.forEach(item -> {
            importing.setEmployeeId(item.getId());
            importMapper.insert(importing);
        });
    }


}
