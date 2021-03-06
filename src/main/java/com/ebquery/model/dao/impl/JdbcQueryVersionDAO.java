package com.EBQuery.model.dao.impl;

import com.EBQuery.model.QueryVersion;
import com.EBQuery.model.dao.QueryVersionDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcQueryVersionDAO implements QueryVersionDAO {
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public QueryVersion find(Integer id) {
        String sql = "SELECT * FROM query_versions WHERE id = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);
        QueryVersion queryVersion =
                jdbcTemplate.query(sql, new Object[] { id }, new QueryVersionMapper()).get(0);

        return queryVersion;
    }

    public QueryVersion findByQueryIdAndVersion(Integer id, Integer version) {
        String sql = "SELECT * FROM query_versions WHERE query_id = ? AND version = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);
        QueryVersion queryVersion =
                jdbcTemplate.query(sql, new Object[] { id, version }, new QueryVersionMapper()).get(0);

        return queryVersion;
    }

    public QueryVersion findCurrentByQueryId(Integer id) {
        String sql = "SELECT * FROM query_versions WHERE query_id = ? AND is_current_version = TRUE";

        jdbcTemplate = new JdbcTemplate(dataSource);
        QueryVersion queryVersion =
                jdbcTemplate.query(sql, new Object[] { id }, new QueryVersionMapper()).get(0);

        return queryVersion;
    }

    public List<QueryVersion> findByQueryId(Integer id) {
        String sql = "SELECT * FROM query_versions WHERE query_id = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);
        List<QueryVersion> queryVersions =
                jdbcTemplate.query(sql, new Object[] { id }, new QueryVersionMapper());

        return queryVersions;
    }

    public List<QueryVersion> findPreviousVersionsByQueryId(Integer id) {
        String sql = "SELECT * FROM query_versions WHERE query_id = ? AND is_current_version = FALSE ORDER BY ID DESC";

        jdbcTemplate = new JdbcTemplate(dataSource);
        List<QueryVersion> queryVersions =
                jdbcTemplate.query(sql, new Object[] { id }, new QueryVersionMapper());

        return queryVersions;
    }

    public List<QueryVersion> findAll() {
        String sql = "SELECT * FROM query_versions";

        jdbcTemplate = new JdbcTemplate(dataSource);
        List<QueryVersion> queryVersions =
                jdbcTemplate.query(sql, new QueryVersionMapper());

        return queryVersions;
    }

    public Number insert(final QueryVersion queryVersion) {
        final String sql = "INSERT INTO query_versions (content, version, is_current_version, query_id) VALUES (?, ?, ?, ?)";

        jdbcTemplate = new JdbcTemplate(dataSource);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[] { "id" });

                preparedStatement.setString(1, queryVersion.getContent());
                preparedStatement.setInt(2, queryVersion.getVersion());
                preparedStatement.setBoolean(3, queryVersion.getIsCurrentVersion());
                preparedStatement.setInt(4, queryVersion.getQueryId());

                return preparedStatement;
            }
        };
        jdbcTemplate.update(
                preparedStatementCreator,
                keyHolder
        );

        return keyHolder.getKey();
    }

    public void update(Integer id, QueryVersion queryVersion) {
        String sql = "UPDATE query_versions SET content = ?, version = ?, is_current_version = ? WHERE id = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(
                sql,
                new Object[] {
                        queryVersion.getContent(),
                        queryVersion.getVersion(),
                        queryVersion.getIsCurrentVersion(),
                        id
                }
        );
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM query_versions WHERE id = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[] { id });
    }

    public void deleteByQueryId(Integer id) {
        String sql = "DELETE FROM query_versions WHERE query_id = ?";

        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, new Object[] { id });
    }

    private static class QueryVersionMapper implements RowMapper<QueryVersion> {
        public QueryVersion mapRow(ResultSet rs, int rowNum) throws SQLException {
            QueryVersion queryVersion = new QueryVersion();

            queryVersion.setId(rs.getInt("id"));
            queryVersion.setContent(rs.getString("content"));
            queryVersion.setVersion(rs.getInt("version"));
            queryVersion.setIsCurrentVersion(rs.getBoolean("is_current_version"));
            queryVersion.setQueryId(rs.getInt("query_id"));

            return queryVersion;
        }
    }
}
