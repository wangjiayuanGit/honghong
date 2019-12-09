package com.honghong.dao;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：wangjy
 * @description ：后台数据
 * @date ：2019/12/2 14:41
 */
public interface DataDAO {
    List<Map<String, Object>> showData(Date date, String city);
}
