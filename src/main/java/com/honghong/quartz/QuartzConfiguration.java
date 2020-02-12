package com.honghong.quartz;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @author wangjy
 * @date 2018/12/5 0005 19:08
 */

@Configuration
public class QuartzConfiguration {

    /**
     * 配置每天执行一次的任务
     *
     * @param todayJob
     * @return
     */
    @Bean(name = "todayJobDetail")
    public MethodInvokingJobDetailFactoryBean todayJobJobDetail(TodayJob todayJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(todayJob);
        // 需要执行的方法
        jobDetail.setTargetMethod("task");
        return jobDetail;
    }

    /**
     * 配置每天执行任务的触发器
     *
     * @param todayJobDetail
     * @return
     */
    @Bean(name = "todayTrigger")
    public CronTriggerFactoryBean todayTrigger(JobDetail todayJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(todayJobDetail);
        trigger.setCronExpression("0 0 0 * * ?");
//        trigger.setCronExpression("0 0/2 * * * ?");
        return trigger;
    }

    /**
     * 配置 Scheduler
     *
     * @param todayTrigger
     * @param planTrigger
     * @return
     */
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger todayTrigger, Trigger planTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        bean.setTriggers(todayTrigger, planTrigger);
        return bean;
    }
}

