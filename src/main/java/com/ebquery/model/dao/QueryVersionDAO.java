package com.EBQuery.model.dao;

import com.EBQuery.model.QueryVersion;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface QueryVersionDAO {
    @Cacheable("sql.queryVersions")
    public QueryVersion find(Integer id);

    @Cacheable("sql.queryVersions")
    public QueryVersion findByQueryIdAndVersion(Integer id, Integer version);

    @Cacheable("sql.queryVersions")
    public QueryVersion findCurrentByQueryId(Integer id);

    @Cacheable("sql.queryVersions.lists")
    public List<QueryVersion> findByQueryId(Integer id);

    @Cacheable("sql.queryVersions.lists")
    public List<QueryVersion> findPreviousVersionsByQueryId(Integer id);

    @Cacheable("sql.queryVersions.lists")
    public List<QueryVersion> findAll();

    @CacheEvict(
            value = "sql.queryVersions.lists",
            allEntries = true
    )
    public Number insert(QueryVersion QueryVersion);

    @CacheEvict(
            value = {
                    "sql.queryVersions",
                    "sql.queryVersions.lists"
            },
            allEntries = true)
    public void update(Integer id, QueryVersion QueryVersion);

    @CacheEvict(
            value = {
                    "sql.queryVersions",
                    "sql.queryVersions.lists"
            },
            allEntries = true)
    public void delete(Integer id);

    @CacheEvict(
            value = {
                    "sql.queryVersions",
                    "sql.queryVersions.lists"
            },
            allEntries = true)
    public void deleteByQueryId(Integer id);
}
