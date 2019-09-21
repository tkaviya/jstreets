package net.streets.persistence.dao.super_class;

import net.streets.common.enumeration.StrResponseCode;
import net.streets.common.response.StrResponseObject;
import net.streets.common.structure.Pair;
import net.streets.persistence.entity.super_class.str_entity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 8/11/13
 * Time: 8:30 PM
 */

@Repository
@Transactional
public abstract class AbstractDao<E extends str_entity, I extends Serializable> implements StreetsDaoInterface<E, I> {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private static EntityManager entityManager;

    Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    private Class<E> entityClass;

    protected AbstractDao(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {

        if (entityManager == null) {

            if (entityManagerFactory == null) {
                LOGGER.info("@PersistenceUnit entityManagerFactory is null. Using raw filename");
                entityManagerFactory = Persistence.createEntityManagerFactory("symPersistence");
            }

            LOGGER.info("Creating entity manager from entityManagerFactory");
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }

    @Transactional
    public E refresh(E e) {
        getEntityManager().refresh(e);
        LOGGER.info(">Refreshed " + getEntityClass().getSimpleName() + " with Id " + e.getId() + ": " + e.toString());
        return e;
    }

    @Transactional
    public E saveOrUpdate(str_entity<E> e) {
        LOGGER.info(">Updating entity: " + e.toString());
        getEntityManager().merge(e);
        LOGGER.info(">Flushing data to db");
        getEntityManager().flush();
        LOGGER.info(">Updated " + getEntityClass().getSimpleName() + " with Id " + e.getId() + ": " + e.toString());
        return (E) e;
    }

    @Transactional
    public E save(str_entity<E> e) {
        LOGGER.info(">Saving new entity to database: " + e.toString());
        getEntityManager().persist(e);
        LOGGER.info(">Flushing data to db");
        getEntityManager().flush();
        LOGGER.info(">Persisted " + getEntityClass().getSimpleName() + " with Id " + e.getId() + ": " + e.toString());
        return (E) e;
    }

    @Transactional
    public void delete(str_entity<E> e) {
        LOGGER.info(">Deleting entity " + e.toString());
        getEntityManager().remove(e);
        getEntityManager().flush();
    }

    public E findById(I id) {
        LOGGER.info(">" + getEntityClass().getSimpleName() + " findById " + id);
        return getEntityManager().find(getEntityClass(), id);
    }

    public List<E> findAll() {
        LOGGER.info(">findAll " + getEntityClass().getSimpleName());
        return getEntityManager().createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e").getResultList();
    }

    public List<E> findWhere(List<Pair<String, ?>> criterion) {
        return findWhere(criterion, -1);
    }

    public List<E> findWhere(Pair<String, ?> criteria) {
        List conditions = new ArrayList<Pair<String, Object>>();
        conditions.add(criteria);
        return findWhere(conditions, -1);
    }

    public List<E> findWhere(List<Pair<String, ?>> criterion, int maxResults) {
        String conditions = "";

        for (Pair<String, ?> criteria : criterion) {
            if (conditions.length() > 0) {
                conditions += " AND ";
            }
            conditions += criteria.getLeft() + " = '" + criteria.getRight() + "' ";
        }

        LOGGER.info(">" + getEntityClass().getSimpleName() + " findWhere " + conditions);
        LOGGER.info(">" + getEntityClass().getSimpleName() + " findWhere max results = " + maxResults);

        javax.persistence.Query query = getEntityManager().createQuery("SELECT e FROM " +
                getEntityClass().getSimpleName() + " e WHERE " + conditions);

        if (maxResults > 0) {
            query.setMaxResults(maxResults).getResultList();
        }

        return query.getResultList();
    }

    public StrResponseObject<E> findUniqueWhere(List<Pair<String, ?>> criterion) {
        return enforceUnique(findWhere(criterion, 0));
    }

    public <E> StrResponseObject<E> enforceUnique(List<E> list) {
        return new StrResponseObject<E>(StrResponseCode.SUCCESS, list.get(0));
    }
}
