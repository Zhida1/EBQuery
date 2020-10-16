package com.EBQuery.model.dao;

import com.EBQuery.model.DatabaseConnection;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface DatabaseConnectionDAO {
    @Cacheable("sql.databaseConnections")
    public DatabaseConnection find(Integer id);

    @Cacheable("sql.databaseConnections.lists")
    public List<DatabaseConnection> findAll();

    @CacheEvict(
            value = "sql.databaseConnections.lists",
            allEntries = true
    )
    public Number insert(DatabaseConnection databaseConnection);

    @CacheEvict(
            value = {
                    "sql.databaseConnections",
                    "sql.databaseConnections.lists"
            },
            allEntries = true)
    public void update(Integer id, DatabaseConnection databaseConnection);

    @CacheEvict(
            value = {
                    "sql.databaseConnections",
                    "sql.databaseConnections.lists"
            },
            allEntries = true)
    public void delete(Integer id);
}
