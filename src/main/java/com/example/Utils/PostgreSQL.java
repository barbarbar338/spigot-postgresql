package com.example.Utils;

import com.example.Example;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public class PostgreSQL {
    public Connection connect(@Nonnull Example plugin) {
        plugin.getLogger().log(Level.INFO, "Connecting to PostgreSQL DB...");

        String host = plugin.config.getString("postgres_host");
        String db = plugin.config.getString("postgres_db");
        String user = plugin.config.getString("postgres_user");
        String password = plugin.config.getString("postgres_password");
        String port = plugin.config.getString("postgres_port");

        Connection conn = null;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(host + "/" + db);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("port", port);
        config.setDriverClassName(org.postgresql.Driver.class.getName());

        HikariDataSource ds = new HikariDataSource(config);

        try {
            conn = ds.getConnection();
        } catch (SQLException err) {
            plugin.getLogger().log(Level.WARNING, err.getMessage());
            ds.close();
        }

        plugin.getLogger().log(Level.INFO, "Connected to PostgreSQL DB!");

        return conn;
    }
}
