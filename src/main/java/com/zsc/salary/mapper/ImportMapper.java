package com.zsc.salary.mapper;

import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.model.pojo.Import;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsc.salary.model.vo.ImportVo;

import java.util.List;

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
     * @return Import显示的数据
     */
    List<ImportVo> listImportVo();
}
