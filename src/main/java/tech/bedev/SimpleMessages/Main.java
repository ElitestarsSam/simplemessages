package tech.bedev.SimpleMessages;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import tech.bedev.SimpleMessages.Commands.MainCmd;
import tech.bedev.SimpleMessages.Commands.TabCompleters.MainCmdTab;
import tech.bedev.SimpleMessages.Config.ConfigManager;

import java.io.IOException;

public class Main extends JavaPlugin implements Listener {

    private ConfigManager cfgm = new ConfigManager(this);

    @Override
    public void onEnable() {
        this.getLogger().info(ChatColor.AQUA + "SimpleMessages " + ChatColor.YELLOW + "v" + this.getDescription().getVersion() + ChatColor.GREEN + " has been Enabled");
        cfgm.setup();
        this.loadConfig();
        this.loadCommands();
        this.loadEvents();
    }

    public void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.getPlayerData().options().copyDefaults(true);
        this.savePlayerData();
    }

    public void loadCommands() {
        this.getCommand("sm").setExecutor(new MainCmd());
        this.getCommand("sm").setTabCompleter(new MainCmdTab());
    }

    public void loadEvents() {
        this.getServer().getPluginManager().registerEvents(this, this);
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



    @EventHandler
    public void updateMessage(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.getPlayerData().getBoolean("Users." + player.getUniqueId() + ".UpdateChecker")) {
            if (player.hasPermission("sm.update")) {
                new UpdateChecker(this, 83376).getVersion(version -> {
                    if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                        player.sendMessage(ChatColor.YELLOW + "A new version of " + ChatColor.AQUA + "" + ChatColor.BOLD + "SimpleMessages " + ChatColor.GRAY + "(" + ChatColor.WHITE + version + ChatColor.GRAY + ")" + ChatColor.YELLOW + " is available.");
                        player.sendMessage(ChatColor.YELLOW + "Download the new version at " + ChatColor.GRAY + "https://www.be-development.tech/simplemessages");
                    }
                });
            }
        }
    }
}
