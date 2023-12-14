package com.github.karlkm.ctweaks.commands;

import com.github.karlkm.ctweaks.items.tools.blinkpearl.BlinkPearlTool;
import com.github.karlkm.ctweaks.items.tools.tunnelerspickaxe.TunnelersPickaxeTool;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CTweaksCommand implements CommandExecutor, TabCompleter {
    JavaPlugin plugin;
    FileConfiguration config;

    public CTweaksCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        if (!sender.hasPermission("ctweaks.command.ctweaks")) {
            sender.sendMessage("You do not have permission to use this command!");
            return false;
        }

        if (command.getLabel().equals("ctweaks")) {
            if (args.length == 0) {
                sender.sendMessage("You need to specify a subcommand! (usage: /ctweaks [item] <item>)");
                return false;
            }
        }

        if (args[0].equals("item")) {
            if (args.length == 1) {
                sender.sendMessage("You need to specify an item! (usage: /ctweaks item <item>)");
                return false;
            }

            if (args[1].equals("blinkpearl")) {
                if (!config.getBoolean("items.tools.blinkpearl.enable")) {
                    sender.sendMessage("Blink Pearl is disabled! (see config.yml)");
                    return true;
                }
                Player p = (Player) sender;
                ItemStack blinkPearl = BlinkPearlTool.createBlinkPearl();
                p.getInventory().addItem(blinkPearl);
                return true;
            }

            if (args[1].equals("tunnelerspickaxe")) {
                if (!config.getBoolean("items.tools.tunnelerspickaxe.enable")) {
                    sender.sendMessage("Tunneler's Pickaxe is disabled! (see config.yml)");
                    return true;
                }
                Player p = (Player) sender;
                ItemStack tunnelersPickaxe = TunnelersPickaxeTool.createTunnelersPickaxe();
                p.getInventory().addItem(tunnelersPickaxe);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, String[] args) {
        List<String> subcommands = List.of("item");
        List<String> items = List.of("blinkpearl", "tunnelerspickaxe");

        if (args.length == 1) {
            return subcommands;
        }

        if (args.length == 2 && args[0].equals("item")) {
            return items;
        }

        return null;
    }
}
