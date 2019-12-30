package com.honghong.util;

import com.honghong.model.topic.TopicDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ：wangjy
 * @description ：出镜率
 * @date ：2019/9/3
 */
public class AppearanceRate {
    private static final int TWO_DAYS = 48;

    /**
     * @param list 原始数据集合(老数据的集合)
     * @param num  返回集合元素数量
     * @return 返回集合
     */
    public static List<TopicDO> getList(List<TopicDO> list, Integer num) {
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
        int size = list.size() >= num ? num : list.size();
        for (int i = 0; i < size; i++) {
            resultTopicDOS.add(list.get(i));
        }
        return resultTopicDOS;
    }

    /**
     * @param list 原始数据集合(新数据的集合)
     * @return topicDOS 原数据中随机的10%
     */
    public static List<TopicDO> getList(List<TopicDO> list) {
        List<TopicDO> topicDOS = new ArrayList<>();
        int size = (int) (list.size() * 0.1);
        for (int i = 0; i < size; i++) {
            Random random = new Random();
            int n = random.nextInt(list.size());
            topicDOS.add(list.get(n));
        }
        return topicDOS;
    }

}
