package tech.bedev.SimpleMessages.Commands.CustomisableCommands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tech.bedev.SimpleMessages.Main;

public class ApplyCmd implements Listener , CommandExecutor {

    Main plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("apply")) {
                if (plugin.getConfig().getBoolean("Apply.Enabled")) {
                    if (!sender.hasPermission("sm.apply")) {
                        sender.sendMessage(plugin.msg(plugin.getLang().getString("Errors.NoPermission")));
                    } else {
                        String cmdText = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Apply.Message")
                                .replaceAll("%playercount%", String.valueOf(Bukkit.getOfflinePlayers().length))
                                .replaceAll("%player%", sender.getName())
                                .replaceAll("%version%", plugin.getDescription().getVersion()));
                        cmdText = PlaceholderAPI.setPlaceholders((Player) sender, cmdText);
                        sender.sendMessage(cmdText);
                    }
                }
            }
        } else {
            sender.sendMessage(plugin.msg(plugin.getLang().getString("Errors.MustBePlayer")));
        }
        return true;
    }
}
