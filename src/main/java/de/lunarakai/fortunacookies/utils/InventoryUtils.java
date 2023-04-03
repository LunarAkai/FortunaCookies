package de.lunarakai.fortunacookies.utils;

import de.lunarakai.fortunacookies.FortunaCookies;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {
    public Inventory createFortuneCookieInventory(Player player, Integer size, ChatColor chatColor, String title, ItemStack cookie, ItemStack fortuneTelling) {
        Inventory inv = Bukkit.createInventory(player, size, chatColor + title);
        inv.setItem(12, cookie);
        inv.setItem(14, fortuneTelling);
        return inv;
    }

    public int getEmptySlots(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack[] content = inventory.getContents();
        int i = 0;
        for(ItemStack item : content)
            if(item != null && item.getType() != Material.AIR && item.getType() != Material.COOKIE) {
                i++;
            }
        return 36 - i;
    }

    public void givePlayerFortuneCookie(int numberOfCookies, Player player, FortunaCookies main) {

        ItemStack paperCookie = new ItemStack(Material.PAPER);
        // TODO: change paper to non-placeable chest (or a head with a nice texture idk)
        // https://minecraft-heads.com/custom-heads/food%20&%20drinks/14695-box-of-chocolate-closed

        ItemMeta meta = paperCookie.getItemMeta();

        meta.displayName(Component.text(main.getConfig().getString("Translatables.FortuneCookieTranslatable")));

        NamespacedKey key = new NamespacedKey(main, "fortune-cookie-key");

        List<Component> fortuneCookieLore = new ArrayList<>();
        fortuneCookieLore.add(Component.text(main.getConfig().getString("Translatables.FortuneCookieRightClickTranslatable")));
        meta.lore(fortuneCookieLore);

        meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
        paperCookie.setItemMeta(meta);

        paperCookie.setAmount(numberOfCookies);

        player.getInventory().addItem(paperCookie);
    }
}
