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
import java.sql.Statement;
import java.util.logging.Level;

public class CreateCommand implements CommandExecutor {

    private final Example plugin;

    public CreateCommand(@Nonnull Example plugin) {
        this.plugin = plugin;
    }

    private boolean createData(String pid, String pname) {
        boolean status = false;

        try {
            String createQuery = "INSERT INTO DUMMY(ID,CONTENT) VALUES(?,?)";
            PreparedStatement preparedStatement = plugin.connection.prepareStatement(createQuery,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, pid);
            preparedStatement.setString(2, pname);

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
            String pname = player.getName();

            boolean status = createData(pid, pname);

            TextComponent message;
            if (status) {
                message = new TextComponent(ChatColor.GREEN + "Dummy data created!");
            } else {
                message = new TextComponent(
                        ChatColor.RED + "An error occurred while creating dummy data, see console!");
            }

            player.spigot().sendMessage(message);
        }

        return true;
    }
}
