package com.hutgin.dao.jdbc;

import com.hutgin.dao.Dao;
import com.hutgin.entity.Entity;
import com.hutgin.entity.Table;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcDao implements Dao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    protected NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Entity> findAll(Table type) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Entity get(Table type, Object id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
