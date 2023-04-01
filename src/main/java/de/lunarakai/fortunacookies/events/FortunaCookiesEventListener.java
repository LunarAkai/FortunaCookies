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

        //TODO: doesnt find event :?

        FortunaCookies.LOGGER.log(Level.FINE, String.valueOf(e.getAction()));

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR) {
            ItemStack itemStack = e.getItem();
            if(itemStack != null) {
                if (itemStack.getType() == Material.PAPER && itemStack.getItemMeta().displayName().toString().equals("Fortune Cookie")) {
                    Player player = e.getPlayer();

                    if(itemStack.getAmount() >= 2) {
                        itemStack.setAmount(itemStack.getAmount() - 1);
                    }
                    ItemStack cookie = new ItemStack(Material.COOKIE);

                    player.getInventory().addItem(cookie);
                }
            }
        }
    }
}
