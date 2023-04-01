package de.lunarakai.fortunacookies;

import de.lunarakai.fortunacookies.commands.CommandFortunaCookie;
import de.lunarakai.fortunacookies.events.FortunaCookiesEventListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class FortunaCookies extends JavaPlugin {

    public static Logger LOGGER;

    @Override
    public void onEnable() {

        LOGGER = getLogger();

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new FortunaCookiesEventListener(), this);
        this.getCommand("fortunacookie").setExecutor(new CommandFortunaCookie());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
