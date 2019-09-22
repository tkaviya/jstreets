package net.streets.persistence.dao;

import net.streets.common.structure.Pair;
import net.streets.persistence.entity.super_class.str_entity;
import net.streets.persistence.enumeration.StrResponseCode;
import net.streets.persistence.enumeration.StrResponseObject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Collections.singletonList;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 8/11/13
 * Time: 8:30 PM
 */

@Repository
@Transactional
@PersistenceContext
public class EntityManagerRepo {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private static EntityManager entityManager;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    private final static Integer UNLIMITED_RESULTS = -1;

    private EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            LOGGER.info("Creating entity manager from entityManagerFactory");
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    @Transactional
    public <E extends str_entity> E refresh(E e) {
        try {
	        entityManager = getEntityManager();
            entityManager.refresh(e);
            LOGGER.info("Refreshed " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + e.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not refresh " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + ex.getMessage());
            entityManager = null;
        }
        return e;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public <E extends str_entity> E saveOrUpdate(str_entity<E> e) {
        LOGGER.info("Updating entity: " + e.toString());
	    entityManager = getEntityManager();
	    EntityTransaction transaction = entityManager.getTransaction();
        try {
	        transaction.begin();
	        e = entityManager.merge(e);
	        transaction.commit();
            LOGGER.info("Updated " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + e.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not merge " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + ex.getMessage());
            //if (transaction != null && transaction.isActive()) {
	        //    transaction.rollback();
            //}
            entityManager = null;
            throw new RuntimeException(ex.getMessage());
        }
        return (E) e;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public <E extends str_entity> E save(str_entity<E> e) {
        LOGGER.info("Saving new entity to database: " + e.toString());
	    entityManager = getEntityManager();
	    EntityTransaction transaction = entityManager.getTransaction();
        try {
	        transaction.begin();
            entityManager.persist(e);
	        transaction.commit();
            LOGGER.info("Persisted " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + e.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not persist " + e.getClass().getSimpleName() + " with Id " + e.getId() + ": " + ex.getMessage());
	        //if (transaction != null && transaction.isActive()) {
		    //    transaction.rollback();
	        //}
            entityManager = null;
            throw new RuntimeException(ex);
        }
        return (E) e;
    }

    @Transactional
    public <E extends str_entity> void delete(str_entity<E> e) {
        LOGGER.info("Deleting entity " + e.toString());
	    entityManager = getEntityManager();
	    EntityTransaction transaction = entityManager.getTransaction();
        try {
	        transaction.begin();
            entityManager.remove(e);
	        transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not delete " + e + " : " + ex.getMessage());
            entityManager = null;
        }
    }

    @Transactional
    public <E extends str_entity> Long countWhere(Class<E> entityClass, List<Pair<String, ?>> criterion,
                                                  boolean useLikeQuery, boolean useOrQuery) {
        StringBuilder conditions = new StringBuilder();

        for (Pair<String, ?> criteria : criterion) {
            if (conditions.length() > 0) {
                if (useOrQuery) {
                    conditions.append(" OR ");
                } else {
                    conditions.append(" AND ");
                }
            }
            if (useLikeQuery) {
                conditions.append(criteria.getLeft()).append(" LIKE '%").append(criteria.getRight()).append("%' ");
            } else {
                conditions.append(criteria.getLeft()).append("= '").append(criteria.getRight()).append("' ");
            }
        }

        LOGGER.info(entityClass.getSimpleName() + " countWhere " + conditions);
        String queryString = "SELECT count(e) FROM " + entityClass.getSimpleName() + " e WHERE " + conditions;

	    entityManager = getEntityManager();
        try {
            return (Long) entityManager.createQuery(queryString).getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute countWhere query " + queryString + ": " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    public <E extends str_entity> Long countWhere(Class<E> entityClass, List<Pair<String, ?>> criteria) {
        return countWhere(entityClass, criteria, false, false);
    }

    @Transactional
    public <E extends str_entity> Long countAll(Class<E> entityClass) {
        LOGGER.info("countAll " + entityClass.getSimpleName());
        String queryString = "SELECT count(e) FROM " + entityClass.getSimpleName() + " e";
	    entityManager = getEntityManager();
	    try {
            return (Long) entityManager.createQuery(queryString).getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute countAll query " + queryString + ": " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    @Transactional
    public <E extends str_entity, I extends Serializable> E findById(Class<E> entityClass, I id) {
        LOGGER.info(entityClass.getSimpleName() + " findById " + id);
	    entityManager = getEntityManager();
	    try {
            E result = entityManager.find(entityClass, id);
//            if (result != null) {
//                result.refresh();
//            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute findById query: " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public <E extends str_entity> List<E> findAll(Class<E> entityClass, boolean reverseOrder) {
        LOGGER.info("findAll " + entityClass.getSimpleName());
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e" + (reverseOrder ? " ORDER BY e.id DESC" : "");
	    try {
		    entityManager = getEntityManager();
		    List<E> results = entityManager.createQuery(queryString).getResultList();
//            for (E result : results) {
//                result.refresh();
//            }
            return results;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute findAll query " + queryString + ": " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    public <E extends str_entity> List<E> findAll(Class<E> entityClass) {
        return findAll(entityClass, false);
    }

    @SuppressWarnings("unchecked")
    public <E extends str_entity> List<E> findWhere(Class<E> entityClass, List<Pair<String, ?>> criterion,
                                                    int maxResults, boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery, boolean reverseOrder) {

        StringBuilder conditions = new StringBuilder();

        for (Pair<String, ?> criteria : criterion) {
            if (conditions.length() > 0) {
                if (useOrQuery) {
                    conditions.append(" OR ");
                } else {
                    conditions.append(" AND ");
                }
            }
            String prefix = "", suffix = "";
            if (criteria.getRight() instanceof String && !caseSensitive) {
                prefix = "UPPER(";
                suffix = ")";
            }
            if (useLikeQuery) {
                conditions.append(prefix).append(criteria.getLeft()).append(suffix)
                          .append(" LIKE ").append(prefix)
                          .append("'%").append(criteria.getRight()).append("%'").append(suffix).append(" ");
            } else {
                conditions.append(prefix).append(criteria.getLeft()).append(suffix)
                          .append("= ").append(prefix)
                          .append("'").append(criteria.getRight()).append("'").append(suffix).append(" ");
            }
        }

        LOGGER.info(entityClass.getSimpleName() + " findWhere " + conditions);
        LOGGER.info(entityClass.getSimpleName() + " findWhere max results = " + maxResults);

        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE " +
                conditions + (reverseOrder ? " ORDER BY e.id DESC" : "");
        try {
	        entityManager = getEntityManager();
            Query query = entityManager.createQuery(queryString);
            if (maxResults > 0) {
                query.setMaxResults(maxResults).getResultList();
            }
            List<E> results = query.getResultList();
//            for (E result : results) {
//                result.refresh();
//            }
            return results;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.severe("Could not execute findWhere query " + queryString + ": " + ex.getMessage());
            entityManager = null;
            return null;
        }
    }

    public <E extends str_entity> List<E> findWhere(Class<E> entityClass, List<Pair<String, ?>> criteria) {
        return findWhere(entityClass, criteria, -1, false, false, false, false);
    }

    @SuppressWarnings("unchecked")
    public <E extends str_entity> List<E> findWhere(Class<E> entityClass, Pair<String, ?> criteria) {
        List conditions = new ArrayList<Pair<String, Object>>();
        conditions.add(criteria);
        return findWhere(entityClass, conditions, UNLIMITED_RESULTS,
                false, false, false, false);
    }

    public <E extends str_entity> List<E> findWhere(Class<E> entityClass, Pair<String, ?> criteria, int maxResults) {
        return findWhere(entityClass, singletonList(criteria), maxResults,
                false, false, false, false);
    }

    public <E extends str_entity> List<E> findWhere(Class<E> entityClass, List<Pair<String, ?>> criterion,
                                                    boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery, boolean reverseOrder) {
        return findWhere(entityClass, criterion, -1, caseSensitive, useLikeQuery, useOrQuery, reverseOrder);
    }

    @SuppressWarnings("unchecked")
    public <E extends str_entity> List<E> findWhere(Class<E> entityClass, Pair<String, ?> criteria,
                                                    boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery, boolean reverseOrder) {
        List conditions = new ArrayList<Pair<String, Object>>();
        conditions.add(criteria);
        return findWhere(entityClass, conditions, UNLIMITED_RESULTS, caseSensitive, useLikeQuery, useOrQuery, reverseOrder);
    }

    public <E extends str_entity> List<E> findWhere(Class<E> entityClass, Pair<String, ?> criteria, int maxResults,
                                                    boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery, boolean reverseOrder) {
        return findWhere(entityClass, singletonList(criteria), maxResults, caseSensitive, useLikeQuery, useOrQuery, reverseOrder);
    }

    public <E extends str_entity> E findFirst(Class<E> entityClass, List<Pair<String, ?>> criterion,
                                              boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery) {
        List<E> results = findWhere(entityClass, criterion, 1, caseSensitive, useLikeQuery, useOrQuery, false);
        return (results != null && results.size() > 0) ? results.get(0) : null;
    }

    public <E extends str_entity> E findLast(Class<E> entityClass, List<Pair<String, ?>> criterion,
                                             boolean caseSensitive, boolean useLikeQuery, boolean useOrQuery) {
        List<E> results = findWhere(entityClass, criterion, 1, caseSensitive, useLikeQuery, useOrQuery, true);
        return (results != null && results.size() > 0) ? results.get(0) : null;
    }

    public <E extends str_entity> E findFirst(Class<E> entityClass, List<Pair<String, ?>> criterion) {
        return findFirst(entityClass, criterion, false, false, false);
    }

    public <E extends str_entity> E findLast(Class<E> entityClass, List<Pair<String, ?>> criterion) {
        return findLast(entityClass, criterion, false, false, true);
    }

    public <E extends str_entity> StrResponseObject<E> findUniqueWhere(
		    Class<E> entityClass, List<Pair<String, ?>> criteria) {
        return enforceUnique(findWhere(entityClass, criteria, UNLIMITED_RESULTS,
                false, false, false, false));
    }

    public <E extends str_entity> StrResponseObject<E> findUniqueWhere(
		    Class<E> entityClass, Pair<String, ?> criterion) {
        return enforceUnique(findWhere(entityClass, criterion, UNLIMITED_RESULTS));
    }

    private <E> StrResponseObject<E> enforceUnique(List<E> list) {
        if (list.size() == 1) {
            return new StrResponseObject<>(StrResponseCode.SUCCESS, list.get(0));
        } else if (list.size() > 1) {
            StringBuilder alert = new StringBuilder("Found non unique entries for " + list.get(0).getClass().getSimpleName() + "\r\n\r\n");
            for (E item : list) {
                alert.append(item.toString()).append("\n");
            }
            new Exception(alert.toString()).printStackTrace();
//            sendEmailAlert("SYMBIOSIS_CONTROL_CENTER", "Found non unique entries for " + list.get(0).getClass().getSimpleName(), alert.toString());
            return new StrResponseObject<>(StrResponseCode.GENERAL_ERROR);
        }
        return new StrResponseObject<>(StrResponseCode.DATA_NOT_FOUND);
    }
}
