package com.ce.notebook.service;

import com.ce.notebook.configuration.jpa.EntityToRepository;
import com.ce.notebook.domain.BaseRepository;
import com.ce.notebook.domain.specification.ConnectType;
import com.ce.notebook.domain.specification.SimpleSpecification;
import com.ce.notebook.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * service基类
 *
 * @author: ce
 * @create: 2018-10-25 14:02
 **/
public class BaseService<T> {

    @Autowired
    private EntityToRepository entityToRepository;
    @Autowired
    private ApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public BaseRepository<T, Long> repository = null;

    /*
     * 获取T的实际类对象
     * @author ce
     * @date 18-10-26 下午8:05
     * @param []
     * @return java.lang.Class
    */
    private Class getTClass() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            if (type.getActualTypeArguments().length <= 0) {
                return null;
            }
            return (Class) type.getActualTypeArguments()[0];
        }
        return null;
    }

    @PostConstruct
    public void init () {
        Class clazz = getTClass();
        if (clazz != null) {
            repository = (BaseRepository<T, Long>) applicationContext.getBean(entityToRepository.getRepository(clazz));
        }
    }

    /*
     * create和update都使用save方法
     * @author ce
     * @date 18-10-28 下午2:35  
     * @param [t]  
     * @return T  
    */
    public T save (T t) {
        return repository.save(t);
    }

    public Iterator<T> saveAll (Iterable<T> ts) {
        return repository.saveAll(ts).iterator();
    }

    /*
     * 查询
     * @author ce
     * @date 18-10-28 下午2:35  
     * @param [id]  
     * @return T  
    */
    @CacheEvict
    public T findById (Long id) {
        Optional<T> optional = repository.findById(id);
        return optional.orElse(null);
    }

    public Iterator<T> findAll () {
        Iterable<T> iterable = repository.findAll();
        return iterable.iterator();
    }

    public Iterator<T> findAll (Specification<T> specification) {
        List<T> list = repository.findAll(specification);
        return list.iterator();
    }

    public Iterator<T> findAll (Specification<T> specification, Sort sort) {
        List<T> list = repository.findAll(specification, sort);
        return list.iterator();
    }

    public Iterator<T> findAll (Specification<T> specification, Pageable pageable) {
        Page<T> page = repository.findAll(specification, pageable);
        return page.getContent().iterator();
    }

    public Iterator<T> findAll (T t) {
        SimpleSpecification<T> simpleSpecification = new SimpleSpecification<>();
        simpleSpecification.equal(t);
        simpleSpecification.setConnectType(ConnectType.AND);
        return findAll(simpleSpecification);
    }

    public Iterator<T> findAllById (Iterable<Long> id) {
        Iterable<T> iterable = repository.findAllById(id);
        return iterable.iterator();
    }

    public T update (T newObject) {

        Long id = null;
        T oldObject = null;
        if (newObject != null) {
//            更新的对象未指定id
            if ((id = ObjectUtils.getFieldValue(newObject, "id")) == null) {
                return null;
            } else {
//                查询在数据库中储存的对象
                oldObject = findById(id);
                return repository.save(ObjectUtils.updateObject(newObject, oldObject));
            }
        }
        return  null;
    }
    /*
     * 删除
     * @author ce
     * @date 18-10-28 下午2:38
     * @param [id]
     * @return java.lang.Boolean
    */
    public Boolean deleteById (Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean delete (T t) {
        try {
            repository.delete(t);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean deleteAll (Iterable<T> entities) {
        try {
            repository.deleteAll(entities);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
