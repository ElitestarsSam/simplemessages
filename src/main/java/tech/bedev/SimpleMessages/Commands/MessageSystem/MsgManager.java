package tech.bedev.SimpleMessages.Commands.MessageSystem;

import org.bukkit.entity.Player;
import tech.bedev.SimpleMessages.Main;

import java.util.HashMap;

public class MsgManager {

    Main plugin;

    HashMap<Player, Player> conversations = new HashMap<Player,Player>();

    public MsgManager(Main main) {
        plugin = main;
    }

    public void setReplyTarget(Player messager, Player receiver) {
        conversations.put(messager, receiver);
        conversations.put(receiver, messager);
    }

    public Player getReplyTarget(Player messager) {
        return conversations.get(messager);
    }
}
