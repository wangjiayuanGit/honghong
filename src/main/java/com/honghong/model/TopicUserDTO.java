package com.honghong.model;

import com.honghong.util.ExcelHead;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TopicUserDTO {
    private String content;
    private Integer commentSum;
    private Integer likeSum;
    private Date createdAt;
    private String nickName;
    private String headImage;
    private String city;

    public List<ExcelHead> creatHeader() {
        List<ExcelHead> excelHeads = new ArrayList<>();
        ExcelHead excelHead1 = new ExcelHead("内容", "content", true);
        ExcelHead excelHead2 = new ExcelHead("评论数量", "commentSum", false);
        ExcelHead excelHead3 = new ExcelHead("点赞数量", "likeSum", false);
        ExcelHead excelHead4 = new ExcelHead("创建时间", "createdAt", true);
        ExcelHead excelHead5 = new ExcelHead("昵称", "nickName", true);
        ExcelHead excelHead6 = new ExcelHead("头像", "headImage", true);
        ExcelHead excelHead7 = new ExcelHead("城市", "city", false);
        excelHeads.add(excelHead1);
        excelHeads.add(excelHead2);
        excelHeads.add(excelHead3);
        excelHeads.add(excelHead4);
        excelHeads.add(excelHead5);
        excelHeads.add(excelHead6);
        excelHeads.add(excelHead7);
        return excelHeads;
    }

}
