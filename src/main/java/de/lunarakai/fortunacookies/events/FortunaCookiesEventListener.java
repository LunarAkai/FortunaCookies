package de.lunarakai.fortunacookies.events;

import de.lunarakai.fortunacookies.FortunaCookies;
import de.lunarakai.fortunacookies.utils.ChatUtils;
import de.lunarakai.fortunacookies.utils.InventoryUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FortunaCookiesEventListener implements Listener {

    private FortunaCookies main;
    private ChatUtils chatUtils = new ChatUtils();
    private List<Player> playerList = new ArrayList<>();

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
                    if (itemStack.getType() == Material.PAPER && plainName.equals(main.getConfig().getString("Translatables.FortuneCookieTranslatable"))) {
                        Player player = e.getPlayer();


                        if(itemStack.getAmount() >= 2) {
                            itemStack.setAmount(itemStack.getAmount() - 1);
                        } else if(itemStack.getAmount() == 1) {
                            player.getInventory().removeItem(itemStack);
                        }

                        int emptySlots = getEmptySlots(player);

                        if(emptySlots >= 2) {


                            ItemStack cookie = new ItemStack(Material.COOKIE);
                            ItemStack fortuneTellingPaper = new ItemStack(Material.PAPER);

                            ItemMeta fortuneTellingPaperMeta = fortuneTellingPaper.getItemMeta();
                            fortuneTellingPaperMeta.displayName(Component.text(main.getConfig().getString("Translatables.FortuneTellingTranslatable")));

                            List<Component> ftpLore = new ArrayList<>();

                            String[] fortuneTellingsList = main.getConfig().getStringList("FortuneTellingsList")
                                    .toArray(new String[main.getConfig().getStringList("FortuneTellingsList").size()]);
                            Random random = new Random();

                            String randomFortuneTellingString = fortuneTellingsList[random.nextInt(fortuneTellingsList.length)];

                            ftpLore.add(Component.text(randomFortuneTellingString));
                            fortuneTellingPaperMeta.lore(ftpLore);

                            fortuneTellingPaper.setItemMeta(fortuneTellingPaperMeta);



                            InventoryUtils inventoryUtils = new InventoryUtils();
                            Inventory inv = inventoryUtils.createFortuneCookieInventory(
                                    player,
                                    27,
                                    ChatColor.GOLD,
                                    main.getConfig().getString("Translatables.FortuneCookieTranslatable"),
                                    cookie,
                                    fortuneTellingPaper);

                            player.openInventory(inv);

                            playerList.add(player);

                            //TODO: Give player item if player closes inv while items are still inside
                        } else {
                            chatUtils.sendDefaultMessage(player, main.getConfig().getString("Translatables.WarningNotEnoughInvSlots"));
                        }
                    }
                }
            }
        }
    }

    // list => if player opens inv => add to list
    // check if player is on list if player closes inv
    // give player items from inv if player is on the list

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

    private int getEmptySlots(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack[] content = inventory.getContents();
        int i = 0;
        for(ItemStack item : content)
            if(item != null && item.getType() != Material.AIR && item.getType() != Material.COOKIE) {
                i++;
            }
        return 36 - i;
    }
}
