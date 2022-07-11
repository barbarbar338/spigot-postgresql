package com.example;

import com.example.Utils.PostgreSQL;
import com.example.Utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public final class Example extends JavaPlugin {
    public final PostgreSQL db = new PostgreSQL();
    public final FileConfiguration config = getConfig();
    public Connection connection;

    @Override
    public void onEnable() {
        Utils.handleConfig(this);
        connection = db.connect(this);
        Utils.loadCommands(this);
    }

    @Override
    public void onDisable() {
        try {
            connection.close();
        } catch (SQLException err) {
            getLogger().log(Level.WARNING, err.getMessage());
        }
        getLogger().log(Level.INFO, "PostgreSQL Example disabled!");
    }
}
