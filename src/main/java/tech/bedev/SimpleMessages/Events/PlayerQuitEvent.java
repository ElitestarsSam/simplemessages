package tech.bedev.SimpleMessages.Events;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.bedev.SimpleMessages.Main;

public class PlayerQuitEvent implements Listener {

    Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onPlayerLeave(org.bukkit.event.player.PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("LeaveMessage.Enabled")) {
            String leaveText = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("LeaveMessage.Message")
                            .replaceAll("%playercount%", String.valueOf(Bukkit.getOfflinePlayers().length))
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%version%", plugin.getDescription().getVersion()));
            leaveText = PlaceholderAPI.setPlaceholders(event.getPlayer(), leaveText);
            event.setQuitMessage(leaveText);
        } else {
            event.setQuitMessage("");
        }
    }
}
