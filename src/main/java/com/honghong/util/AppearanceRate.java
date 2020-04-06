package com.honghong.util;

import com.honghong.model.topic.TopicDO;
import com.honghong.quartz.TodayJob;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ：wangjy
 * @description ：出镜率
 * @date ：2019/9/3
 */
public class AppearanceRate {

    /**
     * @param list 原始数据集合(老数据的集合)
     * @param num  返回集合元素数量
     * @return 返回集合
     */
   /* public static List<TopicDO> getOldDataList(List<TopicDO> list, Integer num) {
        List<TopicDO> resultTopicDOS = new ArrayList<>();

        //权重
        List<Integer> weights = new ArrayList<>();
        for (TopicDO topicDO : list) {
            int weight = (topicDO.getLikeSum() + 1000);
        topicDO.setWeight(weight);
        weights.add(topicDO.getWeight());
    }
        //权重数和
        int sum = 1;
        for (Integer integer : weights) {
            sum++;
        }
        //新权重 = 当前权重+随机数
        for (TopicDO topicDO : list) {
            Random random = new Random();
            int r = random.nextInt(sum - 1);
            topicDO.setWeight(topicDO.getWeight() + r);
        }
        //排序
        list = new ArrayList<>(list);
        list.sort((y, x) -> Integer.compare(x.getWeight(), y.getWeight()));
        //分数据池
        List<TopicDO> high = new ArrayList<>();
        List<TopicDO> medium = new ArrayList<>();
        List<TopicDO> low = new ArrayList<>();

        int size = list.size() >= num ? num : list.size();
        for (int i = 0; i < size; i++) {
            resultTopicDOS.add(list.get(i));
        }
        return resultTopicDOS;
    }*/
    public static List<TopicDO> getOldDataList(List<TopicDO> list, Integer num) {
        if (list.size() <= 700) {
            return list;
        }
        List<TopicDO> resultTopicDOS = new ArrayList<>();
        //分数据池
        int h = list.size() / 3;
        List<TopicDO> high = new ArrayList<>();
        List<TopicDO> medium = new ArrayList<>();
        List<TopicDO> low = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i < h) {
                high.add(list.get(i));
            }
            if (i > h && i < 2 * h) {
                medium.add(list.get(i));
            } else {
                low.add(list.get(i));
            }
        }
        resultTopicDOS.addAll(getResult(high, (int) (num * 0.35)));
        resultTopicDOS.addAll(getResult(medium, (int) (num * 0.30)));
        resultTopicDOS.addAll(getResult(low, (int) (num * 0.05)));

        return resultTopicDOS;
    }

    /*
     */
/**
 * @param list 原始数据集合(新数据的集合)
 * @return topicDOS 原数据中随机的10%
 *//*

    public static List<TopicDO> getNewDataList(List<TopicDO> list, int num) {
        if (list.size() <= 300) {
            return list;
        }
        List<TopicDO> topicDOS = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Random random = new Random();
            int n = random.nextInt(list.size() - 1);
            topicDOS.add(list.get(n));
        }
        return topicDOS;
    }
*/

    /**
     * 随机取出n条元素
     *
     * @param list
     * @param num
     * @return
     */
    public static List<TopicDO> getResult(List<TopicDO> list, int num) {
        if (list.size() <= num) {
            return list;
        }
        List<TopicDO> result = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            Random random = new Random();
            int n = random.nextInt(list.size() - 1);
            result.add(list.get(n));
        }
        return result;
    }
}
