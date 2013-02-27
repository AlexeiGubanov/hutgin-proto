package com.hutgin.dao.jdbc;

import com.hutgin.dao.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcExecutor implements Executor {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSourceMeta) {
        this.jdbcTemplate = new JdbcTemplate(dataSourceMeta);
    }

    @Override
    public void runCommand(String command) {
        this.jdbcTemplate.execute(command);
    }
}
