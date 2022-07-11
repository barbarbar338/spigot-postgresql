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
import java.sql.SQLException;
import java.util.logging.Level;

public class DeleteCommand implements CommandExecutor {

    private final Example plugin;

    public DeleteCommand(@Nonnull Example plugin) {
        this.plugin = plugin;
    }

    private boolean deleteData(String pid) {
        boolean status = false;

        try {
            String deleteQuery = "DELETE FROM DUMMY WHERE ID=?";
            PreparedStatement preparedStatement = plugin.connection.prepareStatement(deleteQuery);

            preparedStatement.setString(1, pid);

            preparedStatement.executeUpdate();

            status = true;
        } catch (SQLException err) {
            plugin.getLogger().log(Level.WARNING, err.getMessage());
        }

        return status;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
            @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String pid = player.getUniqueId().toString();

            boolean status = deleteData(pid);

            TextComponent message;
            if (status) {
                message = new TextComponent(ChatColor.GREEN + "Dummy data deleted!");
            } else {
                message = new TextComponent(
                        ChatColor.RED + "An error occurred while deleting dummy data, see console!");
            }

            player.spigot().sendMessage(message);
        }

        return true;
    }
}
