package com.honghong.common;

/**
 * @author ：wangjy
 * @description ：性别枚举类
 * @date ：2019/9/6 10:49
 */
public enum Gender {
    /**
     * 未知性别
     */
    UNKNOW(0, "未知"),
    /**
     * 男
     */
    MAN(1, "先生"),
    /**
     * 女
     */
    WOMAN(2, "女士");

    private Byte value;
    private String name;

    Gender(int value, String name) {
        this.value = (byte) value;
        this.name = name;
    }

    public Byte getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}