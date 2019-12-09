package com.honghong.quartz;

import com.honghong.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 每天凌晨查找当天需要执行的计划
 *
 * @author wangjy
 * @date 2018/12/5 0005 10:50
 */
//@Component
//@EnableScheduling
public class TodayJob implements ApplicationRunner {
    @Autowired
    private TopicService topicService;
    Logger logger = LoggerFactory.getLogger(TodayJob.class);

    public void task() {
        System.out.println("任务2执行....");
        logger.info("定时任务启动，每天凌晨定时查库，并设置排行信息" + "--" + System.currentTimeMillis());
        topicService.ranking();
    }

    /**
     * 程序启动时立即执行方法
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        task();
    }
}
