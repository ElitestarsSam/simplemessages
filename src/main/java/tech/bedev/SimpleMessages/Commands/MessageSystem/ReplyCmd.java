package tech.bedev.SimpleMessages.Commands.MessageSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tech.bedev.SimpleMessages.Main;

public class ReplyCmd implements Listener , CommandExecutor {

    Main plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("sm.msg")) {
                Player messager = (Player) sender;
                if (plugin.msgM.getReplyTarget(messager) == null) {
                    messager.sendMessage(plugin.msg(plugin.getConfig().getString("Messaging.Errors.NoConversation")));
                    return true;
                }
                Player receiver = plugin.msgM.getReplyTarget(messager);
                String message = "";
                for (int i = 0; i < args.length; i++) {
                    message += " " + args[i];
                }
                messager.sendMessage(plugin.msg(plugin.getConfig().getString("Messaging.Messages.Outgoing"))
                        .replaceAll("%sender%", messager.getName()).replaceAll("%receiver%", receiver.getName()).replaceAll("%message%", message));
                receiver.sendMessage(plugin.msg(plugin.getConfig().getString("Messaging.Messages.Incoming"))
                        .replaceAll("%sender%", messager.getName()).replaceAll("%receiver%", receiver.getName()).replaceAll("%message%", message));
            } else { sender.sendMessage(plugin.msg(plugin.getLang().getString("Errors.NoPermission"))); }
        }
        return true;
    }
}
