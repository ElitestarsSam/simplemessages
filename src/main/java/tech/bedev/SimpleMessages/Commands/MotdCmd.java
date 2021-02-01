package tech.bedev.SimpleMessages.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tech.bedev.SimpleMessages.Main;

public class MotdCmd implements Listener, CommandExecutor {

    Main plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("motd")) {
            if (player.hasPermission("sm.motd")) {
                if (args.length == 0) {
                    for (int i = 0; i < plugin.getConfig().getList("Motd.Message").size(); i++) {
                        player.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("Motd.Message").get(i).toString())
                                .replaceAll("%playercount%", String.valueOf(Bukkit.getOfflinePlayers().length))
                                .replaceAll("%player%", player.getName())
                                .replaceAll("%version%", plugin.getDescription().getVersion()));
                    }
                } else if (args[0].equalsIgnoreCase("enable")) {
                    if (sender instanceof Player) {
                        if (args.length == 1) {
                            plugin.getData().set("Users." + player.getUniqueId() + ".Motd", true);
                            plugin.saveData();
                            plugin.reloadData();
                            sender.sendMessage(plugin.msg(plugin.getLang().getString("Messages.EnableMotd")));
                            return true;
                        }
                    } else {
                        sender.sendMessage(plugin.msg(plugin.getLang().getString("Errors.MustBePlayer")));
                    }
                } else if (args[0].equalsIgnoreCase("disable")) {
                    if (sender instanceof Player) {
                        if (args.length == 1) {
                            plugin.getData().set("Users." + player.getUniqueId() + ".Motd", false);
                            plugin.saveData();
                            plugin.reloadData();
                            sender.sendMessage(plugin.msg(plugin.getLang().getString("Messages.DisableMotd")));
                            return true;
                        }
                    } else {
                        sender.sendMessage(plugin.msg(plugin.getLang().getString("Errors.MustBePlayer")));
                    }
                }
            } else {
                sender.sendMessage(plugin.msg(plugin.getLang().getString("Errors.NoPermission")));
            }
        }
        return true;
    }
}
