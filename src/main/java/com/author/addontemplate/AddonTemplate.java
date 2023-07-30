package com.author.addontemplate;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AddonTemplate extends JavaPlugin {

    private AddonManager addonManager = null;
    private SkriptAddon addon;

    @Override
    public void onEnable() {
        if (this.addonManager == null) {
            this.addonManager = new AddonManager(this);
        }
        if (!this.addonManager.canRegisterSafely()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.addon = Skript.getAddon(this);
//        loadBStats(1234); // Change '1234' with your bStats plugin id
    }

    public AddonManager getAddonManager() {
        return this.addonManager;
    }

    public SkriptAddon getSkriptAddon() {
        return this.addon;
    }


    private void loadBStats(int id) {
        Metrics bStatsMetric = new Metrics(this, id);
        bStatsMetric.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));
    }


}
