package tech.bedev.SimpleMessages.Commands.TabCompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MotdCmdTab implements TabCompleter {

    List<String> arg0 = new ArrayList<String>();

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (arg0.isEmpty()) {
            if (sender.hasPermission("sm.motd")) {
                arg0.add("enable");
                arg0.add("disable");
            }
        }

        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arg0) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        return null;
    }
}
