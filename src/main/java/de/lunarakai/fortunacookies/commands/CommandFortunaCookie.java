package de.lunarakai.fortunacookies.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandFortunaCookie implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack paperCookie = new ItemStack(Material.PAPER);

            ItemMeta meta = paperCookie.getItemMeta();

            meta.displayName(Component.text("Fortune Cookie"));
            paperCookie.setItemMeta(meta);

            player.getInventory().addItem(paperCookie);

        }


        return true;
    }
}
