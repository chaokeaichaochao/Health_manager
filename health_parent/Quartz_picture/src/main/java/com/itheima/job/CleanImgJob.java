package com.itheima.job;

import com.itheima.dao.SetmealDao;
import com.itheima.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CleanImgJob {

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);
    @Autowired
    private SetmealDao setmealDao;

    /**
     * 清理七牛上的垃圾图片
     */
    public void clean7NiuImgJob() {
        log.info("开始执行清理7牛上垃圾图片任务~~~");
        //查询七牛上所有的图片
        List<String> imgIn7Niu = QiNiuUtils.listFile();
        log.info("七牛上共有{}张图片", imgIn7Niu.size());
        //查询数据库中所有图片
        List<String> imgInDB = setmealDao.getImgs();
        log.info("数据库共有{}张图片", imgInDB == null ? 0 : imgInDB.size());
        //七牛上图片集合减去数据库图片
        if (imgInDB != null) {

            imgIn7Niu.removeAll(imgInDB);
            log.info("需要清理的垃圾图片共有{}张", imgIn7Niu.size());
            //调用七牛工具删除垃圾图片
            String[] imgNeed2Delete = imgIn7Niu.toArray(new String[]{});
            QiNiuUtils.removeFiles(imgNeed2Delete);
            log.info("清理7牛上垃圾图片任务执行完毕....");

        }


    }
}
