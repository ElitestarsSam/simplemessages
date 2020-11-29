package tech.bedev.SimpleMessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker implements Listener {

    Main plugin = Main.getPlugin(Main.class);

    private JavaPlugin pl;

    public UpdateChecker(JavaPlugin pl, int resourceId) {
        this.pl = pl;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.pl, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=83376")
                    .openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                this.pl.getLogger().info(ChatColor.RED + "Cannot look for updates: " + exception.getMessage());
            }
        });
    }

    @EventHandler
    public void updateMessage(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("sm.update")) {
            if (plugin.getPlayerData().getBoolean("Users." + player.getUniqueId() + ".UpdateChecker")) {
                new UpdateChecker(pl, 83376).getVersion(version -> {
                    if (!pl.getDescription().getVersion().equalsIgnoreCase(version)) {
                        player.sendMessage(ChatColor.YELLOW + "A new version of " + ChatColor.AQUA + "" + ChatColor.BOLD + "SimpleMessages " + ChatColor.GRAY + "(" + ChatColor.WHITE + version + ChatColor.GRAY + ")" + ChatColor.YELLOW + " is available.");
                        player.sendMessage(ChatColor.YELLOW + "Download the new version at " + ChatColor.GRAY + "https://www.be-development.tech/simplemessages");
                    }
                });
            }
        }
    }
}
