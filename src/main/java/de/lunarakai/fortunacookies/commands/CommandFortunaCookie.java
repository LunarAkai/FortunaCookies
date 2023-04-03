package de.lunarakai.fortunacookies.commands;

import de.lunarakai.fortunacookies.FortunaCookies;
import de.lunarakai.fortunacookies.utils.ChatUtils;
import de.lunarakai.fortunacookies.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFortunaCookie implements CommandExecutor {

    private FortunaCookies main;
    private ChatUtils chatUtils = new ChatUtils();
    private InventoryUtils inventoryUtils = new InventoryUtils();

    public CommandFortunaCookie(FortunaCookies main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                inventoryUtils.givePlayerFortuneCookie(1, player, main);
                return true;
            }
            if(args.length >= 1) {
                Integer numberOfCookies = 1;
                if(args[0].equals("help")){
                    chatUtils.sendDefaultMessage(player, "FortunaCookies by LunarAkai");
                    chatUtils.sendDefaultMessage(player, "Usage:");
                    chatUtils.sendDefaultMessage(player, "/fc [optional] <number of cookies|help> [optional] <Player>");
                    return true;
                }
                try {
                    numberOfCookies = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    chatUtils.sendWarningMessage(player, "The first argument needs to be an integer (number)!");
                    return true;
                }
                if(args.length == 1) {
                    inventoryUtils.givePlayerFortuneCookie(numberOfCookies, player, main);
                    return true;
                }
                if(args.length == 2) {
                    Player argPlayer = Bukkit.getPlayer(args[1]);
                    inventoryUtils.givePlayerFortuneCookie(numberOfCookies, argPlayer, main);
                    return true;
                }
                if(args.length >= 3) {
                    chatUtils.sendDefaultMessage(player, "The number of arguments is too high :v");
                    chatUtils.sendDefaultMessage(player, "See '/fc help' for usage.");
                }
            }
        }
        return true;
    }


}
