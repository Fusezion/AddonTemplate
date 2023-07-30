package com.author.addontemplate;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.IOException;
import java.util.logging.Logger;

public class AddonManager {

    public static final Version EARLIEST_SKRIPT_VERSION = new Version(2, 6, 4); // new Version("2.7.0-beta3") for betas/alphas
    public static final Version EARLIEST_MC_VERSION = new Version(1, 19, 4);
    private final PluginManager pluginManager;
    private final AddonTemplate plugin;
    private final Plugin skriptPlugin;
    private final Logger logger;

    public AddonManager(AddonTemplate plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.pluginManager = plugin.getServer().getPluginManager();
        this.skriptPlugin = pluginManager.getPlugin("Skript");
    }

    public boolean canRegisterSafely() {
        // Plugin was already enabled on start up and some other plugin is
        if (skriptPlugin == null) {
            logger.info("The dependency Skript is not installed on this server, you can download the latest version of Skript at https://github.com/SkriptLang/Skript/releases/latest.");
            return false;
        } else if (!skriptPlugin.isEnabled()) {
            logger.info("The dependency Skript was found, however the plugin seems to be disabled.");
            logger.info("this could mean something in forcing this to be loaded before Skript. Plugin disabling!");
            return false;
        } else if (!Skript.isAcceptRegistrations()) {
            Plugin plugMan = pluginManager.getPlugin("PlugMan");
            logger.info("Skript is not longer accepting registrations, addons can no longer be registered!");
            if (plugMan != null && plugMan.isEnabled()) {
                logger.info("It seems like you're running 'PlugMan'.");
                logger.info("If you're trying to reload/enable this addon with PluginMan... you can't.");
                logger.info("Please restart your server!");
            } else {
                logger.info("No clue how this could happen.");
                logger.info("It seems like something is delaying the addon from loading, which is after skript stops accepting registrations.");
            }
            return false;
        } else if (Skript.getVersion().isSmallerThan(EARLIEST_SKRIPT_VERSION)) {
            logger.info("You're currently running an unsupported Skript version, you can find the latest release of skript at https://github.com/.SkriptLang/Skript/releases/latest.");
            return false;
        } else if (!Skript.isRunningMinecraft(EARLIEST_MC_VERSION)) {
            logger.info("You're currently running an unsupported Minecraft server version this addon only supports " + EARLIEST_MC_VERSION + "+!");
            return false;
        }
        this.loadSkriptElements();
        return true;
    }

    public void loadSkriptElements() {
        SkriptAddon skriptAddon = Skript.registerAddon(plugin);
        skriptAddon.setLanguageFileDirectory("lang");
        try {
            skriptAddon.loadClasses("java.com", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
            pluginManager.disablePlugin(plugin);
        }
    }

}
