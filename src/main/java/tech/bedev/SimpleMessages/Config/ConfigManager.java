package tech.bedev.SimpleMessages.Config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tech.bedev.SimpleMessages.Main;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final Main plugin;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration playercfg;
    public File playerdata;
    public FileConfiguration langcfg;
    public File langfile;

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        playerdata = new File(plugin.getDataFolder(), "database.db");

        if (!playerdata.exists()) {
            try {
                playerdata.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().warning(ChatColor.RED + "Could not create the player data file!");
            }
        }

        playercfg = YamlConfiguration.loadConfiguration(playerdata);

        langfile = new File(plugin.getDataFolder(), "lang.yml");

        if (!langfile.exists()) {
            try {
                langfile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().warning(ChatColor.RED + "Could not create the lang file!");
            }
        }

        langcfg = YamlConfiguration.loadConfiguration(langfile);
    }
}
