package com.honghong.util;

import com.honghong.model.topic.LikeVO;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：wangjy
 * @description ：Map 转Object
 * @date ：2020/2/25 18:40
 */
public class BeanMapUtils {
    /**
     * 将对象属性转化为map结合
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map集合中的数据转化为指定对象的同名属性中
     */
    public static <T> T mapToBean(Map<String, Object> map,Class<T> clazz) throws Exception {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("likeSum", 73);
        map.put("content", "哈哈哈哈");
        map.put("userId", 1L);
        map.put("topicId", 47L);
        map.put("nickname", "自由");
        try {
            LikeVO likeVO = BeanMapUtils.mapToBean(map, LikeVO.class);
            System.out.println(likeVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
