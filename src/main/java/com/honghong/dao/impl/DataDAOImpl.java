package com.honghong.dao.impl;

import com.honghong.dao.DataDAO;
import com.honghong.model.topic.TopicDO;
import com.honghong.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author ：wangjy
 * @description ：后台数据
 * @date ：2019/12/2 14:42
 */
@Service
public class DataDAOImpl implements DataDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Map<String, Object>> showData(Date date, String city) {
        if (date == null) {
            date = new Date();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<TopicDO> root = cq.from(TopicDO.class);
        Date start = DateUtils.getStartOfDay(date);
        Predicate predicate = cb.greaterThanOrEqualTo(root.get("createdAt"), start);
        predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt"), date));
        if (StringUtils.isNotBlank(city)) {
            predicate = cb.and(predicate, cb.equal(root.get("city"), city));
        }
        Path<String> truth = root.get("truth");
        Expression<Integer> likeSum = cb.sum(root.get("likeSum").as(Integer.class));
        Expression<Integer> commentSum = cb.sum(root.get("commentSum").as(Integer.class));

        cq.select(cb.tuple(truth.alias("truth"), likeSum.alias("likeSum"), commentSum.alias("commentSum"))).where(predicate).groupBy(truth);
        return getMaps(entityManager.createQuery(cq).getResultList());
    }


    private List<Map<String, Object>> getMaps(List<Tuple> list) {
        List<Map<String, Object>> resultMap = new ArrayList<>();
        for (Tuple tuple : list) {
            Map<String, Object> map = new HashMap<>(16);
            for (TupleElement tupleElement : tuple.getElements()) {
                map.put(tupleElement.getAlias(), tuple.get(tupleElement));
            }
            resultMap.add(map);
        }
        return resultMap;
    }
}
