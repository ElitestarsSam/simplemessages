package tech.bedev.SimpleMessages.Commands.TabCompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCmdTab implements TabCompleter {

    List<String> arg0 = new ArrayList<String>();
    List<String> updatearg = new ArrayList<>();
    List<String> commandsarg = new ArrayList<>();
    List<String> motdarg = new ArrayList<>();

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (arg0.isEmpty()) {
            if (sender.hasPermission("sm.update")) {
                arg0.add("updatechecker");
            }
            if (sender.hasPermission("sm.reload")) {
                arg0.add("reload");
            }
            if (sender.hasPermission("sm.help")) {
                arg0.add("help");
            }
            if (sender.hasPermission("sm.website") || sender.hasPermission("sm.store") || sender.hasPermission("sm.discord") || sender.hasPermission("sm.apply") || sender.hasPermission("sm.teamspeak") || sender.hasPermission("sm.twitter") || sender.hasPermission("sm.youtube") || sender.hasPermission("sm.twitch")) {
                arg0.add("command");
            }
            if (sender.hasPermission("sm.motd")) {
                arg0.add("motd");
            }
        }
            if (motdarg.isEmpty()) {
                motdarg.add("enable");
                motdarg.add("disable");
            }
			if (updatearg.isEmpty()) {
				updatearg.add("enable");
				updatearg.add("disable");
			}
			if (commandsarg.isEmpty()) {
			    commandsarg.add("website");
                commandsarg.add("store");
                commandsarg.add("discord");
                commandsarg.add("apply");
                commandsarg.add("teamspeak");
                commandsarg.add("twitter");
                commandsarg.add("youtube");
                commandsarg.add("twitch");
            }

        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arg0) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }
            return result;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("updatechecker")) {
                for (String a : updatearg) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("motd")) {
                for (String a : motdarg) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("command")) {
                for (String a : commandsarg) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
        }
        return null;
    }
}