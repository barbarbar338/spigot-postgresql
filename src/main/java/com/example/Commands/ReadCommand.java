package com.example.Commands;

import com.example.Example;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ReadCommand implements CommandExecutor {
    private final Example plugin;

    public ReadCommand(@Nonnull Example plugin) {
        this.plugin = plugin;
    }

    private Map<String, String> readData(@Nonnull String pid) {
        Map<String, String> data = new HashMap<String, String>();

        try {
            String readQuery = "SELECT ID,CONTENT FROM DUMMY WHERE ID=?";
            PreparedStatement preparedStatement = plugin.connection.prepareStatement(readQuery);
            preparedStatement.setString(1, pid);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String content = rs.getString("content");
                String id = rs.getString("id");
                data.put("id", id);
                data.put("content", content);
            }
        } catch (SQLException err) {
            plugin.getLogger().log(Level.WARNING, err.getMessage());
        }

        return data;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
            @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String pid = player.getUniqueId().toString();

            TextComponent message;
            Map<String, String> data = readData(pid);
            if (!data.containsKey("id")) {
                message = new TextComponent(ChatColor.RED + "Dummy data not found, use /create command");
            } else {
                message = new TextComponent(
                        ChatColor.YELLOW + "Dummy data: id=" + data.get("id") + ",content=" + data.get("content"));
            }

            player.spigot().sendMessage(message);
        }

        return true;
    }
}
