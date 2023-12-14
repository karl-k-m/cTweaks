package com.github.karlkm.ctweaks;

import com.github.karlkm.ctweaks.commands.CTweaksCommand;
import com.github.karlkm.ctweaks.items.tools.blinkpearl.BlinkPearlListener;
import com.github.karlkm.ctweaks.items.tools.blinkpearl.BlinkPearlTool;
import com.github.karlkm.ctweaks.items.tools.tunnelerspickaxe.TunnelersPickaxeListener;
import com.github.karlkm.ctweaks.items.tools.tunnelerspickaxe.TunnelersPickaxeTool;
import com.github.karlkm.ctweaks.functions.dispenerpickupfromcauldron.DispenserInteractCauldronListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public final class CTweaks extends JavaPlugin {
    FileConfiguration config = this.getConfig();
    PluginManager pluginManager = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        initializeFunctions();
        initializeItems();

        Objects.requireNonNull(this.getCommand("ctweaks")).setExecutor(new CTweaksCommand(this));
    }

    @Override
    public void onDisable() {
    }

    private void initializeFunctions() {
        // Dispenser Interact Cauldron
        if (config.getBoolean("functions.dispenserInteractCauldron.enable")) {
            pluginManager.registerEvents(new DispenserInteractCauldronListener(this), this);
        }
    }

    private void initializeItems() {
        // Initialize Tools

        // Blink Pearl
        if (config.getBoolean("items.tools.blinkpearl.enable")) {
            pluginManager.registerEvents(new BlinkPearlListener(this), this);

            ItemStack blinkPearl = BlinkPearlTool.createBlinkPearl();
            NamespacedKey key = new NamespacedKey(this, "blink_pearl");
            ShapelessRecipe recipe = new ShapelessRecipe(key, blinkPearl);
            recipe.addIngredient(Material.ENDER_PEARL);
            Material craftingRecipeItem = Material.getMaterial(Objects.requireNonNull(config.getString("items.tools.blinkpearl.craftingRecipeItem")));
            recipe.addIngredient(craftingRecipeItem);
            getServer().addRecipe(recipe);
        }

        // Tunnelers Pickaxe
        if (config.getBoolean("items.tools.tunnelerspickaxe.enable")) {
            getServer().getPluginManager().registerEvents(new TunnelersPickaxeListener(), this);

            ItemStack tunnelersPickaxe = TunnelersPickaxeTool.createTunnelersPickaxe();
            NamespacedKey key = new NamespacedKey(this, "tunnelers_pickaxe");
            ShapelessRecipe recipe = new ShapelessRecipe(key, tunnelersPickaxe);
            recipe.addIngredient(Material.DIAMOND_PICKAXE);
            Material craftingRecipeItem = Material.getMaterial(Objects.requireNonNull(config.getString("items.tools.tunnelerspickaxe.craftingRecipeItem")));
            recipe.addIngredient(craftingRecipeItem);
            getServer().addRecipe(recipe);
        }
    }
}
