package tech.bedev.SimpleMessages.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tech.bedev.SimpleMessages.Main;

public class ToggleDeathMessages implements Listener , CommandExecutor {

    Main plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("toggledeathmessages")) {
            if (sender.hasPermission("sm.toggledeathmessages")) {
                if (sender instanceof Player) {
                    if (!Main.toggledeathlist.contains(player.getUniqueId())) {
                        player.sendMessage(ChatColor.GREEN + "Death Messages have been turned on!");
                        Main.toggledeathlist.add(player.getUniqueId());
                    } else {
                        player.sendMessage(ChatColor.RED + "Death Messages have been turned off!");
                        Main.toggledeathlist.remove(player.getUniqueId());
                    }
                } else {
                    sender.sendMessage(plugin.msg(plugin.getLang().getString("Errors.MustBePlayer")));
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
        }
        return true;
    }
}
