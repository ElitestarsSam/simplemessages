package tech.bedev.SimpleMessages.Config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tech.bedev.SimpleMessages.Main;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private Main plugin = Main.getPlugin(Main.class);

    public FileConfiguration playercfg;
    public File playerdata;

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        playerdata = new File(plugin.getDataFolder(), "playerdata.db");

        if (!playerdata.exists()) {
            try {
                playerdata.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().warning(ChatColor.RED + "Could not create the player data file!");
            }
        }

        playercfg = YamlConfiguration.loadConfiguration(playerdata);
    }
}
