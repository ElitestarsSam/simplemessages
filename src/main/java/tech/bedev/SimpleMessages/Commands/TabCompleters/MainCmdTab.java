package tech.bedev.SimpleMessages.Commands.TabCompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCmdTab implements TabCompleter {

    List<String> arg0 = new ArrayList<String>();
    List<String> updatearg = new ArrayList<>();
    List<String> noarg = new ArrayList<>();

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (arg0.isEmpty()) {
            if (sender.hasPermission("sm.update")) {
                arg0.add("update");
            }
            if (sender.hasPermission("sm.reload")) {
                arg0.add("reload");
            }
            if (sender.hasPermission("sm.help")) {
                arg0.add("help");
            }
        }
			if (updatearg.isEmpty()) {
				updatearg.add("enable");
				updatearg.add("disable");
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
            if (args[0].equalsIgnoreCase("help")) {
                for (String a : noarg) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("reload")) {
                for (String a : noarg) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
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
        return null;
    }
}