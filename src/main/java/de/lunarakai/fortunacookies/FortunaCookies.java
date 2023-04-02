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

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new FortunaCookiesEventListener(this), this);
        this.getCommand("fortunacookie").setExecutor(new CommandFortunaCookie(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
