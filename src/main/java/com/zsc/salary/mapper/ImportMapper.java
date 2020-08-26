package com.zsc.salary.mapper;

import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.model.pojo.Import;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.ImportVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
public interface ImportMapper extends BaseMapper<Import> {

    /**
     * 批量插入数据
     * @param list excel导入的数据
     */
    void insertImport(List<UploadData> list);

    /**
     * 获取Import显示的数据
     * @param map 查询条件
     * @return Import显示的数据
     */
    List<ImportVo> listImportVo(Map<String, Object> map);

    /**
     * 获取当天导入员工考勤表中的重复数据
     * @return 考勤表中的重复数据
     */
    List<ImportVo> listImportVoRepeatData();
}
