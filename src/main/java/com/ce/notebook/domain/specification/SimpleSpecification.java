package com.ce.notebook.domain.specification;

import com.ce.notebook.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义Specification用于Criteria查询
 *
 * @author: ce
 * @create: 2018-11-03 16:38
 **/
public class SimpleSpecification<T> implements Specification<T> {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSpecification.class);

//    条件连接的方式 默认And
    private ConnectType connectType = ConnectType.AND;
//    保存条件的map String: 对象属性名 Object: 属性值
    private Map<String, Object> predicateMap;

    public SimpleSpecification () {
        predicateMap = new HashMap<>();
    }

    /*
     * 添加equal查询条件
     * @author ce
     * @date 18-11-3 下午6:58
     * @param [t]
     * @return void
     */
    public void equal (String propertyName, Object propertyKey) {
        predicateMap.put(propertyName, propertyKey);
    }

    public void equal (Map<String, Object> objectMap) {
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            equal(entry.getKey(), entry.getValue());
        }
    }

    public void equal (T t) {
        Map<String, Object> objectMap = ObjectUtils.JavaBean2Map(t);
        equal(objectMap);
    }

    public ConnectType getConnectType() {
        return connectType;
    }

    public void setConnectType(ConnectType connectType) {
        this.connectType = connectType;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : predicateMap.entrySet()) {
            list.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
        }

        Predicate[] predicates = list.toArray(new Predicate[0]);
        Predicate result = null;
        switch (connectType) {
            case AND: result = criteriaBuilder.and(predicates);
            break;
            case OR: result = criteriaBuilder.or(predicates);
        }

        criteriaQuery.where(result);
        return criteriaQuery.getRestriction();
    }

}
