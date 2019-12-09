package com.honghong.common;

/**
 * @author ：wangjy
 * @description ：评论主体类型
 * @date ：2019/9/23 16:43
 */
public enum CommentType {
    /**
     * 回复
     */
    REPLY(0, "回复"),
    /**
     * 评论
     */
    TOPIC_COMMENT(1, "对话题的评论");
//    CHILD_COMMENT(2, "对评论的评论");

    private Integer value;
    private String name;

    CommentType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }}
