package com.EBQuery.model.dao;

import com.EBQuery.model.Query;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface QueryDAO {
    @Cacheable("sql.queries")
    public Query find(Integer id);

    @Cacheable("sql.queries")
    public Query findByKey(String key);

    @Cacheable("sql.queries.lists")
    public List<Query> findByDatabaseConnectionId(Integer id);

    @Cacheable("sql.queries.lists")
    public List<Query> findAll();

    @CacheEvict(
            value = "sql.queries.lists",
            allEntries = true
    )
    public Number insert(Query query);

    @CacheEvict(
            value = {
                    "sql.queries",
                    "sql.queries.lists"
            },
            allEntries = true)
    public void update(Integer id, Query query);

    @CacheEvict(
            value = {
                    "sql.queries",
                    "sql.queries.lists"
            },
            allEntries = true)
    public void delete(Integer id);

    @CacheEvict(
            value = {
                    "sql.queries",
                    "sql.queries.lists"
            },
            allEntries = true)
    public void deleteByDatabaseConnectionId(Integer id);
}
