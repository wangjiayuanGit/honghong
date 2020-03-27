package com.honghong.util;

import lombok.Data;

/**
 * @author ：wangjy
 * @description ：Excel表头与实体类映射
 * @date ：2019/7/10 10:18
 */
@Data
public class ExcelHead {
    /**
     * Excel名
     */
    private String excelName;
    /**
     * 实体类属性名
     */
    private String entityName;
    /**
     * 值必填
     */
    private boolean required = false;

    public ExcelHead() {
    }

    public ExcelHead(String excelName, String entityName) {
        this.excelName = excelName;
        this.entityName = entityName;
    }

    public ExcelHead(String excelName, String entityName, boolean required) {
        this.excelName = excelName;
        this.entityName = entityName;
        this.required = required;
    }
}
