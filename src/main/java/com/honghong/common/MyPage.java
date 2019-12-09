package com.honghong.common;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wangjy
 * @date 2019/9/3
 */
@Data
public class MyPage<T> {
    public MyPage() {

    }

    public MyPage(Page<T> page) {
        this.content = page.getContent();
        this.totalElement = page.getTotalElements();
    }

    private List<T> content;
    private Long totalElement;

}
