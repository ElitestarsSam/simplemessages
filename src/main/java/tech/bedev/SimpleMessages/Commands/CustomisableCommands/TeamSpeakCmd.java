package tech.bedev.SimpleMessages.Commands.CustomisableCommands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tech.bedev.SimpleMessages.Main;

public class TeamSpeakCmd implements Listener, CommandExecutor {

    Main plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("teamspeak")) {
                if (plugin.getConfig().getBoolean("TeamSpeak.Enabled")) {
                    if (!sender.hasPermission("sm.teamspeak")) {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                    } else {
                        String cmdText = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TeamSpeak.Message"));
                        cmdText = PlaceholderAPI.setPlaceholders((Player) sender, cmdText);
                        sender.sendMessage(cmdText);
                    }
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
        }
        return true;
    }
}
