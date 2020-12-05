package tech.bedev.SimpleMessages.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tech.bedev.SimpleMessages.Main;
import tech.bedev.SimpleMessages.UpdateChecker;


public class MainCmd implements CommandExecutor, Listener {

    Main plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("sm")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Main.Header")).replaceAll("%version%", plugin.getDescription().getVersion()));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &bAuthors&8: &f" + plugin.getDescription().getAuthors()));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &bDescription&8: &f" + plugin.getDescription().getDescription()));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &bDiscord&8: &fhttps://www.be-development.tech/discord"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFor commands please do &b/sm help"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Main.Footer")).replaceAll("%version%", plugin.getDescription().getVersion()));
                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("sm.reload")) {
                    sender.sendMessage((Object) ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                } else {
                    if (args.length == 1) {
                        plugin.reloadConfig();
                        plugin.saveDefaultConfig();
                        sender.sendMessage(ChatColor.GREEN + "Reloaded config");
                    }
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                if (!sender.hasPermission("sm.help")) {
                    sender.sendMessage((Object) ChatColor.RED + "You do not have permission to use this command!");
                    return true;
                } else {
                    if (args.length == 1) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Help.Header").replaceAll("%version%", plugin.getDescription().getVersion())));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &b/sm <reload/version/updatechecker/help>&8: &fMain command"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &b/apply&8: &fApply command"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &b/discord&8: &fDiscord command"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &b/store&8: &fStore command"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &b/teamspeak&8: &fTeamSpeak command"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &b/twitch&8: &fTwitch command"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &b/twitter&8: &fTwitter command"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &b/website&8: &fWebsite command"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &9> &b/youtube&8: &fYouTube command"));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Help.Footer").replaceAll("%version%", plugin.getDescription().getVersion())));
                        return true;
                    }
                }
            } else if (args[0].equalsIgnoreCase("command")) {

            } else if (args[0].equalsIgnoreCase("updatechecker")) {
                if (args.length == 1) {
                    if (!sender.hasPermission("sm.update")) {
                        sender.sendMessage(
                                (Object) ChatColor.RED + "You do not have permission to use this command!");
                        return true;
                    } else {
                        new UpdateChecker(plugin, 83376).getVersion(version -> {
                            if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                                sender.sendMessage(ChatColor.YELLOW + "A new version of " + ChatColor.AQUA + "" + ChatColor.BOLD + "SimpleMessages " + ChatColor.GRAY + "(" + ChatColor.WHITE + version + ChatColor.GRAY + ")" + ChatColor.YELLOW + " is available.");
                                sender.sendMessage(ChatColor.YELLOW + "Download the new version at " + ChatColor.GRAY + "https://www.be-development.tech/simplemessages");
                            } else {
                                sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "SimpleMessages"
                                        + ChatColor.YELLOW + " is up to date!");
                            }
                        });
                    }
                } else if (args[1].equalsIgnoreCase("enable")) {
                    if (sender instanceof Player) {
                        if (args.length == 2) {
                            plugin.getData().set("Users." + player.getUniqueId() + ".UpdateChecker", true);
                            plugin.saveData();
                            plugin.reloadData();
                            sender.sendMessage(ChatColor.GREEN + "Enabled update messages for self");
                            return true;
                        }
                    }
                } else if (args[1].equalsIgnoreCase("disable")) {
                    if (sender instanceof Player) {
                        if (args.length == 2) {
                            plugin.getData().set("Users." + player.getUniqueId() + ".UpdateChecker", false);
                            plugin.saveData();
                            plugin.reloadData();
                            sender.sendMessage(ChatColor.RED + "Disabled update messages for self");
                            return true;
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("motd")) {
                if (args.length == 1) {
                    if (player.hasPermission("sm.motd")) {
                        for (int i = 0; i < plugin.getConfig().getList("Motd.Message").size(); i++) {
                            player.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("Motd.Message").get(i).toString())
                                    .replaceAll("%playercount%", String.valueOf(Bukkit.getOfflinePlayers().length))
                                    .replaceAll("%player%", player.getName())
                                    .replaceAll("%version%", plugin.getDescription().getVersion()));
                        }
                    }
                } else if (args[1].equalsIgnoreCase("enable")) {
                    if (sender instanceof Player) {
                        if (args.length == 2) {
                            plugin.getData().set("Users." + player.getUniqueId() + ".Motd", true);
                            plugin.saveData();
                            plugin.reloadData();
                            sender.sendMessage(ChatColor.GREEN + "Enabled motd for self");
                            return true;
                        }
                    }
                } else if (args[1].equalsIgnoreCase("disable")) {
                    if (sender instanceof Player) {
                        if (args.length == 2) {
                            plugin.getData().set("Users." + player.getUniqueId() + ".Motd", false);
                            plugin.saveData();
                            plugin.reloadData();
                            sender.sendMessage(ChatColor.RED + "Disabled motd for self");
                            return true;
                        }
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid operation.");
            }
        }
        return true;
    }
}
