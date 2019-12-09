package com.honghong.util;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Description page工具类
 *
 * @author wangjy
 * @date 2019/9/3
 */
@Data
public class PageUtils {
    public static final int PAGE = 1;
    public static final int SIZE = 10;
    public static final int MODEL_ALL = -1;
    @ApiModelProperty(value = "页码")
    private Integer page;
    @ApiModelProperty(value = "分页大小")
    private Integer size;

    public PageUtils() {
        this.page = PAGE;
        this.size = SIZE;
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(page == null ? PageUtils.PAGE : page - 1, size == null ? SIZE : size);
    }

    public PageRequest getSortPageRequest(Sort sort) {
        return PageRequest.of(page == null ? PageUtils.PAGE : page - 1, size == null ? SIZE : size, sort);
    }
}
