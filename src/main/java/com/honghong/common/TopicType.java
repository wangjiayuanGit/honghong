package com.honghong.common;

/**
 * @author ：wangjy
 * @description ：话题类型枚举类型
 * @date ：2019/9/23 15:55
 */
public enum TopicType {
    /**
     *
     */
    DEMANDS(0, "需求类"),
    NON_DEMAND(1, "非需求类"),
    OTHER(2, "其他");

    private Integer value;
    private String name;

    TopicType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}