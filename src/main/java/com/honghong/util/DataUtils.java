package com.honghong.util;

import com.honghong.model.topic.TopicDO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author ：wangjy
 * @description ：数据工具类
 * @date ：2019/10/10 15:09
 */
public class DataUtils {
    private final static Long ONE_HOUR = 1000 * 3600L;

    /**
     * 是否是新数据
     *
     * @param date
     * @return
     */
    public static boolean isNewData(Date date) {
        Date now = new Date();
        return now.getTime() - date.getTime() < ONE_HOUR;
    }

    public static List<TopicDO> dieOut(List<TopicDO> list) {
        for (TopicDO topicDO : list) {
            double n = (double) (topicDO.getLikeSum()) / topicDO.getCreatedAt().getTime();
            topicDO.setEfficiency(n);
        }
        list = new ArrayList<>(list);
        list.sort((y, x) -> Double.compare(x.getEfficiency(), y.getEfficiency()));
        //淘汰后剩余的条目数
        int size = (int) (list.size() * 0.78);
        //淘汰后剩余的数据
        List<TopicDO> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    /**
     * 乱序输出list
     *
     * @param sourceList
     * @param <V>
     * @return
     */
    public static <V> List<V> randomList(List<V> sourceList) {
        if (sourceList == null || sourceList.size() == 0) {
            return sourceList;
        }
        List<V> random = new ArrayList<V>(sourceList.size());
        do {
            int index = Math.abs(new Random().nextInt(sourceList.size()));
            random.add(sourceList.remove(index));
        } while (sourceList.size() > 0);
        return random;
    }
}
