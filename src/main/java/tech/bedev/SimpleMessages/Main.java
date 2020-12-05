package tech.bedev.SimpleMessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import tech.bedev.SimpleMessages.Commands.CustomisableCommands.*;
import tech.bedev.SimpleMessages.Commands.MainCmd;
import tech.bedev.SimpleMessages.Commands.TabCompleters.MainCmdTab;
import tech.bedev.SimpleMessages.Config.ConfigManager;
import tech.bedev.SimpleMessages.Events.PlayerDeathEvent;

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
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required for SimpleMessages to work!");
            Bukkit.shutdown();
        }
    }

    public void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.getData().options().copyDefaults(true);
        this.saveData();
    }

    public void loadCommands() {
        this.getCommand("sm").setExecutor(new MainCmd());
        this.getCommand("sm").setTabCompleter(new MainCmdTab());
        this.getCommand("apply").setExecutor(new ApplyCmd());
        this.getCommand("discord").setExecutor(new DiscordCmd());
        this.getCommand("store").setExecutor(new StoreCmd());
        this.getCommand("teamspeak").setExecutor(new TeamSpeakCmd());
        this.getCommand("twitter").setExecutor(new TwitterCmd());
        this.getCommand("twitch").setExecutor(new TwitchCmd());
        this.getCommand("website").setExecutor(new WebsiteCmd());
        this.getCommand("youtube").setExecutor(new YouTubeCmd());
    }

    public void loadEvents() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new tech.bedev.SimpleMessages.Events.PlayerJoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new tech.bedev.SimpleMessages.Events.PlayerQuitEvent(), this);
        this.getServer().getPluginManager().registerEvents(new tech.bedev.SimpleMessages.Events.PlayerDeathEvent(), this);
    }

    public FileConfiguration getData() {
        return cfgm.playercfg;
    }

    public void saveData() {
        try {
            cfgm.playercfg.save(cfgm.playerdata);
        } catch (IOException e) {
            this.getLogger().warning(ChatColor.RED + "Could not save the player data file!");
        }
    }

    public void reloadData() {
        cfgm.playercfg = YamlConfiguration.loadConfiguration(cfgm.playerdata);
    }
}
