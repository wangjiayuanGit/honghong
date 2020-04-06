package com.honghong.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ：wangjy
 * @description ：日期工具类
 * @date ：2019/12/2 15:07
 */
public class DateUtils {

    private static Calendar initCalendar() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    // 获得某天最小时间
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    //获取某天的最大时间
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        ;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当月1号0点
     *
     * @return
     */
    public static Date getStartOfTheMonth() {
        return initCalendar().getTime();
    }

    public static Date getEndOfTheMouth() {
        //将当前月加1；
        Calendar calendar = initCalendar();
        calendar.add(Calendar.MONTH, 1);
        //在当前月的下一月基础上减去1毫秒
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println("今天开始时间：" + getStartOfDay(new Date()));
        System.out.println("今天结束时间：" + getEndOfDay(new Date()));
        System.out.println("本月开始时间" + getStartOfTheMonth());
        System.out.println("本月结束时间" + getEndOfTheMouth());
        System.out.println("本月开始时间" + getStartOfTheMonth());

    }
}
