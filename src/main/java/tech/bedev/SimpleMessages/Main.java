package tech.bedev.SimpleMessages;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tech.bedev.SimpleMessages.Commands.MainCmd;
import tech.bedev.SimpleMessages.Commands.TabCompleters.MainCmdTab;
import tech.bedev.SimpleMessages.Config.ConfigManager;

import java.io.IOException;

public class Main extends JavaPlugin implements Listener {

    private ConfigManager cfgm = new ConfigManager();

    @Override
    public void onEnable() {
        this.getLogger().info(ChatColor.AQUA + "SimpleMessages " + ChatColor.YELLOW + "v" + this.getDescription().getVersion() + ChatColor.GREEN + " has been Enabled");
        cfgm.setup();
        loadConfig();
        loadCommands();
    }

    public void loadConfig() {
        this.getPlayerData().options().copyDefaults(true);
        this.savePlayerData();
    }

    public void loadCommands() {
        this.getCommand("sm").setExecutor(new MainCmd());
        this.getCommand("sm").setTabCompleter(new MainCmdTab());
    }

    public FileConfiguration getPlayerData() {
        return cfgm.playercfg;
    }

    public void savePlayerData() {
        try {
            cfgm.playercfg.save(cfgm.playerdata);
        } catch (IOException e) {
            this.getLogger().warning(ChatColor.RED + "Could not save the player data file!");
        }
    }

    public void reloadPlayerData() {
        cfgm.playercfg = YamlConfiguration.loadConfiguration(cfgm.playerdata);
    }
}
