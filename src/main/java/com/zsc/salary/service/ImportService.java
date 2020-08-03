package com.zsc.salary.service;

import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.model.pojo.Import;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
public interface ImportService extends IService<Import> {

    /**
     * 插入
     * @param list 存入的数据
     */
    void insertImport(List<UploadData> list);

}
