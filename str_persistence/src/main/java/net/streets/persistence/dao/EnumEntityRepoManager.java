package net.streets.persistence.dao;

import net.streets.common.structure.Pair;
import net.streets.persistence.entity.super_class.str_enum_entity;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 * *
 * Created:     14 / 01 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class EnumEntityRepoManager {

    private static final Logger logger = Logger.getLogger(EnumEntityRepoManager.class.getSimpleName());
    private static HashMap<String, HashMap<String, str_enum_entity>> ENUM_ENTITY_CACHE = new HashMap<>();

    public static <T extends str_enum_entity> List<T> findEnabled(Class<T> entityClass) {

        if (entityClass == null) return null;

        //always pull from database because we are not sure if the ENUM_ENTITY_CACHE contains all enabled entity's
        List<T> results = getEntityManagerRepo().findWhere(entityClass, new Pair<>("is_enabled", 1));
        logger.warning(format("Found %s %s's in status enabled", results.size(), entityClass.getSimpleName()));

        if (results.size() > 0) {
            if (ENUM_ENTITY_CACHE.get(entityClass.getSimpleName()) == null) {
                HashMap<String, str_enum_entity> cachedValues = new HashMap<>();
                for (str_enum_entity symEnumEntity : results) {
                    cachedValues.put(symEnumEntity.getName(), symEnumEntity);
                }
                ENUM_ENTITY_CACHE.put(entityClass.getSimpleName(), cachedValues);
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    public static <T extends str_enum_entity> T findByName(Class<T> entityClass, String enumName) {

        if (entityClass == null || enumName == null) return null;

        if (ENUM_ENTITY_CACHE.get(entityClass.getSimpleName()) != null) {
            if (ENUM_ENTITY_CACHE.get(entityClass.getSimpleName()).get(enumName) != null) {
                logger.fine(format("Returning cached instance of %s %s", entityClass.getSimpleName(), enumName));
                return (T) ENUM_ENTITY_CACHE.get(entityClass.getSimpleName()).get(enumName);
            }
        }

        List<T> results = getEntityManagerRepo().findWhere(entityClass, new Pair<>("name", enumName));

        if (results == null) {
            logger.warning(format("Could not find %s with name %s", entityClass.getSimpleName(), enumName));
            return null;
        } else if (results.size() != 1) {
            logger.warning(format("Found %s %s's with name %s", results.size(), entityClass.getSimpleName(), enumName));
            return null;
        } else {
            logger.fine(format("Adding cached %s enum entity %s", entityClass.getSimpleName(), enumName));
            ENUM_ENTITY_CACHE.computeIfAbsent(entityClass.getSimpleName(), k -> new HashMap<>());
            ENUM_ENTITY_CACHE.get(entityClass.getSimpleName()).put(enumName, results.get(0));
            return results.get(0);
        }
    }
}
