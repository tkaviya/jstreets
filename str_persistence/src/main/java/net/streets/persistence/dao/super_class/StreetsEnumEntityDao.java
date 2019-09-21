package net.streets.persistence.dao.super_class;

import net.streets.persistence.entity.super_class.str_entity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */
@Repository
@Transactional
public interface StreetsEnumEntityDao<E extends str_entity, I extends Serializable> extends StreetsDaoInterface<E, I> {
    List<E> findEnabled();

    E findEnabledByName(String name);

    E findByName(String name);
}
