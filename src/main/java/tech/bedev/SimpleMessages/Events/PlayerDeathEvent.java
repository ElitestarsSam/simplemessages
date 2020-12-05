package tech.bedev.SimpleMessages.Events;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.bedev.SimpleMessages.Main;

public class PlayerDeathEvent implements Listener {

    Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void playerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();
        Player killer = player.getKiller();
        if (plugin.getConfig().getBoolean("DeathMessage.Enabled")) {
            if (plugin.getConfig().getBoolean("DeathMessage.OnlyPlayer")) {
                if (event.getEntity().getKiller() instanceof Player) {
                    event.setDeathMessage(ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("DeathMessage.Message")
                                    .replaceAll("%death_player%", player.getName())
                                    .replaceAll("%death_killer%", killer.getName())
                                    .replaceAll("%death_item%", killer.getInventory().getItemInHand().getType().name()
                                    .replaceAll("%version%", plugin.getDescription().getVersion()))));
                } else {
                    event.setDeathMessage("");
                }
            } else {
                String deathText = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("DeathMessage.Message")
                                .replaceAll("%death_player%", player.getName())
                                .replaceAll("%death_killer%", killer.getName())
                                .replaceAll("%death_item%", killer.getInventory().getItemInHand().getType().name().toLowerCase().replace("_", " "))
                                .replaceAll("%playercount%", String.valueOf(Bukkit.getOfflinePlayers().length))
                                .replaceAll("%version%", plugin.getDescription().getVersion()));
                deathText = PlaceholderAPI.setPlaceholders(event.getEntity(), deathText);
                event.setDeathMessage(deathText);
            }
        } else {
            event.setDeathMessage("");
        }
    }
}
