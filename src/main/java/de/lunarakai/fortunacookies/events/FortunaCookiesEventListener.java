package de.lunarakai.fortunacookies.events;

import de.lunarakai.fortunacookies.FortunaCookies;
import de.lunarakai.fortunacookies.utils.ChatUtils;
import de.lunarakai.fortunacookies.utils.InventoryUtils;
import de.lunarakai.fortunacookies.utils.ConfigUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FortunaCookiesEventListener implements Listener {

    private FortunaCookies main;
    private ChatUtils chatUtils = new ChatUtils();
    private List<Player> playerList = new ArrayList<>();
    InventoryUtils inventoryUtils = new InventoryUtils();
    ConfigUtils configUtils = new ConfigUtils();

    public FortunaCookiesEventListener(FortunaCookies main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR) {
            ItemStack itemStack = e.getItem();
            if(itemStack != null) {

                Component displayName = itemStack.getItemMeta().displayName();
                if(displayName != null) {
                    String plainName = PlainTextComponentSerializer.plainText().serialize(displayName);

                    NamespacedKey key = new NamespacedKey(main, "fortune-cookie-key");
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    PersistentDataContainer container = itemMeta.getPersistentDataContainer();

                    if (itemStack.getType() == Material.PAPER &&
                            plainName.equals(main.getConfig().getString("Translatables.FortuneCookieTranslatable")) &&
                            container.has(key, PersistentDataType.DOUBLE)) // maybe too much if conditions?
                        {
                        Player player = e.getPlayer();

                        //TODO: if player has more than 2 slots of cookies (for example: slot 1: 50 cookies; slot 2: 1 cookie)
                        //TODO: and player opens cookie from slot 2,
                        //TODO: plugin drains ItemStack from slot 1 instead of slot 2

                        if(itemStack.getAmount() >= 2) {
                            itemStack.setAmount(itemStack.getAmount() - 1);
                        } else if(itemStack.getAmount() == 1) {
                            player.getInventory().removeItem(itemStack);
                        }

                        int emptySlots = inventoryUtils.getEmptySlots(player);

                        if(emptySlots >= 2) {
                            ItemStack cookie = new ItemStack(Material.COOKIE);
                            ItemStack fortuneTellingPaper = new ItemStack(Material.PAPER);

                            ItemMeta fortuneTellingPaperMeta = fortuneTellingPaper.getItemMeta();
                            fortuneTellingPaperMeta.displayName(Component.text(main.getConfig().getString("Translatables.FortuneTellingTranslatable")));

                            List<Component> ftpLore = new ArrayList<>();
                            ftpLore.add(Component.text(configUtils.getRandomFortuneTellingString(main, "FortuneTellingsList")));
                            fortuneTellingPaperMeta.lore(ftpLore);

                            fortuneTellingPaper.setItemMeta(fortuneTellingPaperMeta);

                            Inventory inv = inventoryUtils.createFortuneCookieInventory(
                                    player,
                                    27,
                                    ChatColor.GOLD,
                                    main.getConfig().getString("Translatables.FortuneCookieTranslatable"),
                                    cookie,
                                    fortuneTellingPaper);

                            player.openInventory(inv);
                            player.playSound(player, Sound.BLOCK_BELL_USE, 1f, 2f);

                            playerList.add(player);
                        } else {
                            chatUtils.sendDefaultMessage(player, main.getConfig().getString("Translatables.WarningNotEnoughInvSlots"));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        Inventory inventory = e.getInventory();
        if(playerList.contains(player)) {
            if(inventory.getType().equals(InventoryType.CHEST)) {
                if (inventory.getItem(12) != null ) {
                    ItemStack item = inventory.getItem(12);
                    if(!Objects.equals(item, new ItemStack(Material.AIR)))
                    {
                        ItemStack item1 = inventory.getItem(12);
                        player.getInventory().addItem(item1);
                    }
                }
                if (inventory.getItem(14) != null ) {
                    ItemStack item = inventory.getItem(14);
                    if(!Objects.equals(item, new ItemStack(Material.AIR)))
                    {
                        ItemStack item2 = inventory.getItem(14);
                        player.getInventory().addItem(item2);
                    }
                }
            }
            playerList.remove(player);
        }
    }
}
