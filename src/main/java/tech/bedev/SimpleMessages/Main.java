package tech.bedev.SimpleMessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tech.bedev.SimpleMessages.Commands.CustomisableCommands.*;
import tech.bedev.SimpleMessages.Commands.MainCmd;
import tech.bedev.SimpleMessages.Commands.MessageSystem.MsgCmd;
import tech.bedev.SimpleMessages.Commands.MessageSystem.MsgManager;
import tech.bedev.SimpleMessages.Commands.MessageSystem.ReplyCmd;
import tech.bedev.SimpleMessages.Commands.MotdCmd;
import tech.bedev.SimpleMessages.Commands.TabCompleters.MainCmdTab;
import tech.bedev.SimpleMessages.Commands.TabCompleters.MotdCmdTab;
import tech.bedev.SimpleMessages.Commands.ToggleDeathMessages;
import tech.bedev.SimpleMessages.Config.ConfigManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

    public MsgManager msgM = new MsgManager(this);
    public static List<UUID> toggledeathlist = new ArrayList<UUID>();
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
        this.addDefaults();
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.getData().options().copyDefaults(true);
        this.saveData();
        this.getLang().options().copyDefaults(true);
        this.saveLang();
    }

    public void loadCommands() {
        this.addCmd("sm", new MainCmd());
        this.addTab("sm", new MainCmdTab());
        this.addCmd("apply", new ApplyCmd());
        this.addCmd("discord", new DiscordCmd());
        this.addCmd("store", new StoreCmd());
        this.addCmd("teamspeak", new TeamSpeakCmd());
        this.addCmd("twitter", new TwitterCmd());
        this.addCmd("twitch", new TwitchCmd());
        this.addCmd("website", new WebsiteCmd());
        this.addCmd("youtube", new YouTubeCmd());
        this.addCmd("motd", new MotdCmd());
        this.addTab("motd", new MotdCmdTab());
        this.addCmd("toggledeathmessages", new ToggleDeathMessages());
        this.addCmd("msg", new MsgCmd());
        this.addCmd("reply", new ReplyCmd());
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

    public FileConfiguration getLang() {
        return cfgm.langcfg;
    }

    public void saveLang() {
        try {
            cfgm.langcfg.save(cfgm.langfile);
        } catch (IOException e) {
            this.getLogger().warning(ChatColor.RED + "Could not save the lang file!");
        }
    }

    public void reloadLang() {
        cfgm.langcfg = YamlConfiguration.loadConfiguration(cfgm.langfile);
    }

    public String msg(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void addCmd(String cmd, CommandExecutor executor) {
        this.getCommand(cmd).setExecutor(executor);
    }

    public void addTab(String cmd, TabCompleter completer) {
        this.getCommand(cmd).setTabCompleter(completer);
    }

    public void addDefaults() {
        this.getLang().addDefault("Errors.NoPermission", "&cNo permission!");
        this.getLang().addDefault("Errors.MustBePlayer", "&cYou must be a player to execute this command!");
        this.getLang().addDefault("Messages.EnableMotd", "&aEnabled motd for self");
        this.getLang().addDefault("Messages.DisableMotd", "&cDisabled motd for self");
        this.getLang().addDefault("Messages.EnableUpdate", "&aEnabled update messages for self");
        this.getLang().addDefault("Messages.DisableUpdate", "&cDisabled update messages for self");
        this.getLang().addDefault("Errors.InvalidOperation", "&cInvalid operation!");
        this.getLang().addDefault("Messages.Reload", "&aReloaded config, lang and data");
    }
}
