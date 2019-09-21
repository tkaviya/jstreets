package net.streets.persistence.dao.super_class;

import net.streets.common.response.StrResponseObject;
import net.streets.common.structure.Pair;
import net.streets.persistence.entity.super_class.str_entity;

import java.io.Serializable;
import java.util.List;

public interface StreetsDaoInterface<E extends str_entity, I extends Serializable> {
    E saveOrUpdate(str_entity<E> e);

    E save(str_entity<E> e);

    void delete(str_entity<E> e);

    E refresh(E e);

    Class<E> getEntityClass();

    E findById(I id);

    List<E> findAll();

    List<E> findWhere(List<Pair<String, ?>> criterion, int maxResults);

    StrResponseObject<E> findUniqueWhere(List<Pair<String, ?>> criterion);

    <E> StrResponseObject<E> enforceUnique(List<E> list);
}
