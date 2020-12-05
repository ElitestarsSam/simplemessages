package tech.bedev.SimpleMessages.Events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.bedev.SimpleMessages.Main;
import tech.bedev.SimpleMessages.UpdateChecker;

public class PlayerJoinEvent implements Listener {

    Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean hasPlayed = player.hasPlayedBefore();
        if (!hasPlayed) {
            if (plugin.getConfig().getBoolean("FirstJoinMessage.Enabled")) {
                String firstjoinText = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("FirstJoinMessage.Message")
                                .replaceAll("%playercount%", String.valueOf(Bukkit.getOfflinePlayers().length))
                                .replaceAll("%player%", player.getName())
                                .replaceAll("%version%", plugin.getDescription().getVersion()));
                firstjoinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), firstjoinText);
                event.setJoinMessage(firstjoinText);
            } else {
                if (plugin.getConfig().getBoolean("JoinMessage.Enabled")) {
                    String joinText = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("JoinMessage.Message")
                                    .replaceAll("%playercount%", String.valueOf(Bukkit.getOfflinePlayers().length))
                                    .replaceAll("%player%", player.getName())
                                    .replaceAll("%version%", plugin.getDescription().getVersion()));
                    joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
                    event.setJoinMessage(joinText);
                } else {
                    event.setJoinMessage("");
                }
            }
        } else {
            if (plugin.getConfig().getBoolean("JoinMessage.Enabled")) {
                String joinText = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("JoinMessage.Message")
                                .replaceAll("%playercount%", String.valueOf(Bukkit.getOfflinePlayers().length))
                                .replaceAll("%player%", player.getName())
                                .replaceAll("%version%", plugin.getDescription().getVersion()));
                joinText = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinText);
                event.setJoinMessage(joinText);
            } else {
                event.setJoinMessage("");
            }
        }
        if (plugin.getConfig().getBoolean("Motd.Enabled")) {
            if (plugin.getData().getBoolean("Users." + player.getUniqueId() + ".Motd")) {
                if (player.hasPermission("sm.motd")) {
                    for (int i = 0; i < plugin.getConfig().getList("Motd.Message").size(); i++) {
                        String motdText = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getList("Motd.Message").get(i).toString()
                                .replaceAll("%playercount%", String.valueOf(Bukkit.getOfflinePlayers().length))
                                .replaceAll("%player%", player.getName())
                                .replaceAll("%version%", plugin.getDescription().getVersion()));
                        motdText = PlaceholderAPI.setPlaceholders(event.getPlayer(), motdText);
                        player.sendMessage(motdText);
                    }
                }
            }
        }
        if (plugin.getData().getBoolean("Users." + player.getUniqueId() + ".UpdateChecker")) {
            if (player.hasPermission("sm.update")) {
                new UpdateChecker(plugin, 83376).getVersion(version -> {
                    if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                        player.sendMessage(ChatColor.YELLOW + "A new version of " + ChatColor.AQUA + "" + ChatColor.BOLD + "SimpleMessages " + ChatColor.GRAY + "(" + ChatColor.WHITE + version + ChatColor.GRAY + ")" + ChatColor.YELLOW + " is available.");
                        player.sendMessage(ChatColor.YELLOW + "Download the new version at " + ChatColor.GRAY + "https://www.be-development.tech/simplemessages");
                    }
                });
            }
        }
    }
}
