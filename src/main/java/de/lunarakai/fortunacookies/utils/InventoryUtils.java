package de.lunarakai.fortunacookies.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryUtils {

    private FortunaCookies main;

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

        ItemStack fortuneCookieStack = createHead(
                UUID.fromString(main.getConfig().getString("FortuneCookieHead.FortuneCookieHeadUUID")),
                main.getConfig().getString("FortuneCookieHead.FortuneCookieHeadOwnerName"),
                main.getConfig().getString("FortuneCookieHead.FortuneCookieTextureProperties"));

        ItemMeta meta = fortuneCookieStack.getItemMeta();

        meta.displayName(Component.text(main.getConfig().getString("Translatables.FortuneCookieTranslatable")));

        NamespacedKey key = new NamespacedKey(main, "fortune-cookie-key");

        List<Component> fortuneCookieLore = new ArrayList<>();
        fortuneCookieLore.add(Component.text(main.getConfig().getString("Translatables.FortuneCookieRightClickTranslatable")));
        meta.lore(fortuneCookieLore);

        meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
        fortuneCookieStack.setItemMeta(meta);

        fortuneCookieStack.setAmount(numberOfCookies);

        player.getInventory().addItem(fortuneCookieStack);
    }

    private static ItemStack createHead(UUID ownerUUID, String ownerName, String texturesProperty) {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(ownerUUID, ownerName);
        profile.setProperty(new ProfileProperty("textures", texturesProperty));
        meta.setPlayerProfile(profile);
        stack.setItemMeta(meta);
        return stack;
    }
}
