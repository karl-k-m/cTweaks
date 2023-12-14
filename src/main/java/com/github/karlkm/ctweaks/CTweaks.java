package com.github.karlkm.ctweaks;

import functions.dispenerpickupfromcauldron.DispenserInteractCauldronListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CTweaks extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DispenserInteractCauldronListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
