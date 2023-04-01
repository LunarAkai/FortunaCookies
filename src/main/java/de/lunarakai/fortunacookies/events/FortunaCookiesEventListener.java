package de.lunarakai.fortunacookies.events;

import de.lunarakai.fortunacookies.FortunaCookies;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class FortunaCookiesEventListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        FortunaCookies.LOGGER.log(Level.FINE, String.valueOf(e.getAction()));

        Player player = e.getPlayer();

        ItemStack itemMainHand = player.getInventory().getItemInMainHand();

        FortunaCookies.LOGGER.log(Level.FINE, "Item in Hand: " + itemMainHand.getItemMeta());

        if(itemMainHand != null) {
            if(itemMainHand.getType() == Material.PAPER && itemMainHand.getItemMeta().displayName().toString().equals("Fortune Cookie")) {
                if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    FortunaCookies.LOGGER.log(Level.FINE, "Right clicked!");

                    if(itemMainHand.getAmount() >= 2) {
                        itemMainHand.setAmount(itemMainHand.getAmount() - 1);
                    }

                    ItemStack cookie = new ItemStack(Material.COOKIE);

                    player.getInventory().addItem(cookie);

                    e.setCancelled(true);
                }
            }
        }

    }
}
