package com.hutgin.dao.jdbc;

import com.hutgin.dao.Dao;
import com.hutgin.entity.Entity;
import com.hutgin.entity.Table;
import com.hutgin.util.SqlScriptBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcDao implements Dao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    protected NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public <T extends Map<String, Object>> List<T> findAll(Table type) {
        return (List<T>) jdbcTemplate.queryForList(SqlScriptBuilder.getSelectAllSql(type), new MapSqlParameterSource());
        //  ResultSetMetaData rsMetaData = rs.getMetaData();
    }

    @Override
    public Entity get(Table type, Object id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
