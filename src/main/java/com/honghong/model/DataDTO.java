package com.honghong.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author ：wangjy
 * @description ：
 * @date ：2019/12/2 21:29
 */
@Data
public class DataDTO {
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @NotNull(message = "时间不能为空")
    private Date date;
    private String city;
}
