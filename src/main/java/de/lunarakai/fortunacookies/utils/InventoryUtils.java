package de.lunarakai.fortunacookies.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    public Inventory createFortuneCookieInventory(Player player, Integer size, ChatColor chatColor, String title, ItemStack cookie, ItemStack fortuneTelling) {

        Inventory inv = Bukkit.createInventory(player, size, chatColor + title);

        inv.setItem(12, cookie);
        inv.setItem(14, fortuneTelling);

        return inv;
    }
}
