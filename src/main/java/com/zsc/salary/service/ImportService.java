package com.zsc.salary.service;

import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.model.pojo.Import;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsc.salary.model.dto.ImportDto;
import com.zsc.salary.model.vo.ImportVo;

import java.util.List;
import java.util.Map;

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
     * excel导入数据 并检验用户是否存在 比较耗时
     * @param list 存入的数据
     */
    void insertImport(List<UploadData> list);


    /**
     * 修改导入的数据
     * @param importDto 导入数据的封装
     * @return 影响行数，0则修改失败  1为成功
     */
    int updateImport(ImportDto importDto);

    /**
     * 清空员工的考勤数据
     * @param importId 考勤数据Id
     * @return 影响行数，0则修改失败  1为成功
     */
    int clearImportData(Integer importId);

    /**
     * 删除导入的数据
     * @param importId 导入的ID
     * @return 影响行 0删除失败 1删除成功
     */
    int deleteImport(Integer importId);

    /**
     *  分页获取导入的数据
     * @param pageNo 页码
     * @param pageSize 每页展示数据量
     * @param time 查询的时间范围
     * @return List<ImportVo>数据 和 total 总数据
     */
    Map<String, Object> listImportVo(Integer pageNo, Integer pageSize, String time);

    /**
     *  分页并根据条件获取导入的数据
     * @param map 查询的数据
     * @return List<ImportVo>数据 和 total 总数据
     */
    Map<String, Object> listImportVo(Map<String, Object> map);

    /**
     * 获取导入时当天重复的数据
     * @return 导入时重复的数据
     */
    List<ImportVo> listImportVoRepeatData();

    /**
     * 删除导入的重复数据
     * @param id import ID数组
     */
    void deleteRepeatImportData(Integer[] id);

}
