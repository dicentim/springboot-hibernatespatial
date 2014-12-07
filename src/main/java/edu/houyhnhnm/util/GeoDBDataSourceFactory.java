package edu.houyhnhnm.util;

import geodb.GeoDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public final class GeoDBDataSourceFactory {

    private static Logger LOG = LoggerFactory.getLogger(GeoDBDataSourceFactory.class);

    private final DataSource datasource;

    public GeoDBDataSourceFactory(String databaseName, Resource ddlSchema, Resource dataDump) {
        datasource = createDataSource(databaseName);

        try {
            final Connection connection = datasource.getConnection();

            GeoDB.InitGeoDB(connection);
            LOG.trace("GeoDb is ready.");

            if (ddlSchema != null) {
                executeSQLStatement(connection, ddlSchema);
            }

            if (dataDump != null) {
                executeSQLStatement(connection, ddlSchema);
            }

        } catch (SQLException | IOException e) {
            final DatasourceException datasourceException = new DatasourceException(e);
            LOG.error(e.getMessage(), datasourceException);
            throw datasourceException;
        }
    }

    public GeoDBDataSourceFactory(String databaseName) {
        this(databaseName, null, null);
    }

    private DataSource createDataSource(String databaseName) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        final String dburl = buildDBURL(databaseName);
        dataSource.setUrl(dburl);
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    private String buildDBURL(String databaseName) {
        final String result = String.format("jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1", databaseName);
        return result;
    }

    private void executeSQLStatement(Connection connection, Resource statement) throws IOException, SQLException {
        Objects.requireNonNull(connection, "Connection can not be null.");
        Objects.requireNonNull(statement, "Statement argument can not be null.");
        final String sql = resourceToString(statement);
        final Statement sqlStatement = connection.createStatement();
        sqlStatement.execute(sql);
    }

    private String resourceToString(Resource resource) throws IOException {

        final BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()), 1024);
        final StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line).append('\n');
        }
        br.close();
        return stringBuilder.toString();
    }

    public DataSource getDataSource() {
        return datasource;
    }
}
