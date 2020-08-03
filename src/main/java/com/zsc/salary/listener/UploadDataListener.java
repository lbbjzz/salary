package com.zsc.salary.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.service.ImportService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 上传表格的监听器
 * UploadDataListener 不能被spring管理，要每次读取excel都要new
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/8/3 19:12
 */
@Slf4j
public class UploadDataListener extends AnalysisEventListener<UploadData> {


    private static final int BATCH_COUNT = 5;
    List<UploadData> list = new ArrayList<>();

    private ImportService importService;

    public UploadDataListener(ImportService importService) {
        this.importService = importService;
    }

    @Override
    public void invoke(UploadData uploadData, AnalysisContext analysisContext) {
        list.add(uploadData);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 解析的最后几条数据将走这里进行存放
     * @param analysisContext 内容
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 将数据放到数据库中
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        log.info("存储数据库成功！");
        importService.insertImport(list);
    }
}
