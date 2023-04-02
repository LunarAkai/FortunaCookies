package de.lunarakai.fortunacookies.commands;

import de.lunarakai.fortunacookies.FortunaCookies;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandFortunaCookie implements CommandExecutor {

    private FortunaCookies main;


    public CommandFortunaCookie(FortunaCookies main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack paperCookie = new ItemStack(Material.PAPER); //TODO: change paper to non-placeable chest (or a head with a nice texture idk)

            ItemMeta meta = paperCookie.getItemMeta();

            meta.displayName(Component.text(main.getConfig().getString("Translatables.FortuneCookieTranslatable")));

            List<Component> fortuneCookieLore = new ArrayList<>();
            fortuneCookieLore.add(Component.text(main.getConfig().getString("Translatables.FortuneCookieRightClickTranslatable")));
            meta.lore(fortuneCookieLore);
            paperCookie.setItemMeta(meta);

            player.getInventory().addItem(paperCookie);

        }


        return true;
    }
}
