package com.example.Utils;

import com.example.Commands.CreateCommand;
import com.example.Commands.DeleteCommand;
import com.example.Commands.ReadCommand;
import com.example.Example;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.logging.Level;

public class Utils {
    public static void handleConfig(@Nonnull Example plugin) {
        plugin.config.addDefault("postgres_host", "jdbc:postgresql://localhost");
        plugin.config.addDefault("postgres_user", "postgres");
        plugin.config.addDefault("postgres_db", "postgres");
        plugin.config.addDefault("postgres_password", "1234");
        plugin.config.addDefault("postgres_port", "5432");
        plugin.config.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public static void loadCommands(@Nonnull Example plugin) {
        plugin.getLogger().log(Level.INFO, "Loading commands...");
        Objects.requireNonNull(plugin.getCommand("create")).setExecutor(new CreateCommand(plugin));
        Objects.requireNonNull(plugin.getCommand("read")).setExecutor(new ReadCommand(plugin));
        Objects.requireNonNull(plugin.getCommand("delete")).setExecutor(new DeleteCommand(plugin));
        plugin.getLogger().log(Level.INFO, "Commands loaded!");
    }
}
