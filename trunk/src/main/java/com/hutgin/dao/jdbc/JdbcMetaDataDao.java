package com.hutgin.dao.jdbc;

import com.hutgin.dao.MetaDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Repository
public class JdbcMetaDataDao implements MetaDataDao {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcMetaDataDao.class);

    @Autowired
    private DataSource dataSourceMain;

    /**
     * TODO add throws app exception
     * TODO replace DatabaseMetaData with custom provider (MyDatabaseMetaDataProvider { List<String> getTables()...}
     *
     * @return
     */
    @Override
    public DatabaseMetaData getMetadata() {
        Connection conn = DataSourceUtils.getConnection(dataSourceMain);
        try {
            return conn.getMetaData();
        } catch (SQLException e) {
            LOG.error("Error during extract database metadata: ", e);
        }
        return null;
    }
}
