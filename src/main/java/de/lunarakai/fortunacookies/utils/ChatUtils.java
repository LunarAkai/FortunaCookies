package de.lunarakai.fortunacookies.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtils {
    public void sendDefaultMessage(CommandSender player, String message) {
        player.sendMessage(ChatColor.valueOf("GOLD") + "[FCookies]" + ChatColor.valueOf("WHITE") + " " + message);
    }
    public void sendWarningMessage(CommandSender player, String message) {
        player.sendMessage(ChatColor.valueOf("GOLD") + "[FCookies]" + ChatColor.valueOf("RED") + " " + message);
    }
}
