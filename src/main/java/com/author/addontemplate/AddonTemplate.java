package com.author.addontemplate;

import ch.njol.skript.Skript;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AddonTemplate extends JavaPlugin {

    private AddonManager addonManager;
    private Metrics bStatMetric = null;

    @Override
    public void onEnable() {
        if (this.addonManager == null) {
            this.addonManager = new AddonManager(this);
        }
        if (!this.addonManager.canRegisterSafely()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        loadBStats(1234);
    }

    public AddonManager getAddonManager() {
        return this.addonManager;
    }

    private void loadBStats(int id) {
        this.bStatMetric = new Metrics(this, id);
        bStatMetric.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));
    }


}
