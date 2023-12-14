package com.github.karlkm.ctweaks.functions.dispenerpickupfromcauldron;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class DispenserInteractCauldronListener implements Listener {
    public JavaPlugin plugin;

    public DispenserInteractCauldronListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDispenserInteractCauldron(BlockDispenseEvent event) {
        if (event.getItem().getType() == Material.BUCKET) {
            Block block = getTargetBlock(event);

            if (block.getType() == Material.LAVA_CAULDRON) {
                event.setCancelled(true);

                block.setType(Material.CAULDRON);
                block.getState().update();

                Dispenser dispenser = (Dispenser) event.getBlock().getState();
                Inventory inv = dispenser.getInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> removeItemFromDispenser(inv, new ItemStack(Material.BUCKET)), 1);
                // Delay adding the lava bucket to the dispenser by 1 tick to prevent the bucket from being dispensed.
                Bukkit.getScheduler().runTaskLater(plugin, () -> addItemToDispenser(inv, new ItemStack(Material.LAVA_BUCKET)), 1);
            }

            // TODO: Make this work for only full water cauldrons.
            else if (block.getType() == Material.WATER_CAULDRON) {
                event.setCancelled(true);

                block.setType(Material.CAULDRON);
                block.getState().update();

                Dispenser dispenser = (Dispenser) event.getBlock().getState();
                Inventory inv = dispenser.getInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> removeItemFromDispenser(inv, new ItemStack(Material.BUCKET)), 1);
                // Delay adding the water bucket to the dispenser by 1 tick to prevent the bucket from being dispensed.
                Bukkit.getScheduler().runTaskLater(plugin, () -> addItemToDispenser(inv, new ItemStack(Material.WATER_BUCKET)), 1);
            }
        }

        else if (event.getItem().getType() == Material.WATER_BUCKET) {
            Block block = getTargetBlock(event);

            if (block.getType() == Material.CAULDRON) {
                event.setCancelled(true);

                // TODO: Make it place a full water cauldron instead of a half-full one.
                block.setType(Material.WATER_CAULDRON);
                block.getState().update();

                event.getItem().setType(Material.BUCKET);

                Dispenser dispenser = (Dispenser) event.getBlock().getState();
                Inventory inv = dispenser.getInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> removeItemFromDispenser(inv, new ItemStack(Material.WATER_BUCKET)), 1);
                // Delay adding the water bucket to the dispenser by 1 tick to prevent the bucket from being dispensed.
                Bukkit.getScheduler().runTaskLater(plugin, () -> addItemToDispenser(inv, new ItemStack(Material.BUCKET)), 1);
            }
        }

        else if (event.getItem().getType() == Material.LAVA_BUCKET) {
            Block block = getTargetBlock(event);

            if (block.getType() == Material.CAULDRON) {
                event.setCancelled(true);

                block.setType(Material.LAVA_CAULDRON);
                block.getState().update();

                event.getItem().setType(Material.BUCKET);

                Dispenser dispenser = (Dispenser) event.getBlock().getState();
                Inventory inv = dispenser.getInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> removeItemFromDispenser(inv, new ItemStack(Material.LAVA_BUCKET)), 1);
                // Delay adding the water bucket to the dispenser by 1 tick to prevent the bucket from being dispensed.
                Bukkit.getScheduler().runTaskLater(plugin, () -> addItemToDispenser(inv, new ItemStack(Material.BUCKET)), 1);
            }
        }
    }

    private Block getTargetBlock(BlockDispenseEvent event) {
        Directional directional = (Directional) event.getBlock().getBlockData();
        BlockFace facing = directional.getFacing();

        int x = event.getBlock().getX();
        int y = event.getBlock().getY();
        int z = event.getBlock().getZ();

        int targetX = x + facing.getModX();
        int targetY = y + facing.getModY();
        int targetZ = z + facing.getModZ();

        Block block = Bukkit.getWorld(event.getBlock().getWorld().getUID()).getBlockAt(targetX, targetY, targetZ);

        return block;
    }

    private static void removeItemFromDispenser(Inventory inv, ItemStack item) {
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack itemStack = inv.getItem(i);
            if (itemStack != null && itemStack.getType() == item.getType()) {
                itemStack.setAmount(itemStack.getAmount() - 1);
                inv.setItem(i, itemStack);
                break;
            }
        }
    }

    private static void addItemToDispenser(Inventory inv, ItemStack item) {
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack itemStack = inv.getItem(i);
            if (itemStack == null) {
                inv.setItem(i, item);
                break;
            }

            else if (i == inv.getSize() - 1) {
                Objects.requireNonNull(Objects.requireNonNull(inv.getLocation()).getWorld()).dropItem(inv.getLocation(), item);
            }
        }
    }
}