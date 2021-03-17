package com.fiveeus.filter;

import com.fiveeus.filter.Commands.Filter;
import com.fiveeus.filter.Events.Chat;
import com.fiveeus.filter.Events.Command;
import java.io.File;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Plugin plugin;

    public Main() {
    }

    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        createFilterFile();
        this.getCommand("filter").setExecutor(new Filter());
        this.getServer().getPluginManager().registerEvents(new Chat(), this);
        this.getServer().getPluginManager().registerEvents(new Command(), this);
        this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Filter] " + ChatColor.GRAY + "Enabled Filter v1.2.7");
    }

    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Filter] " + ChatColor.GRAY + "Disabled Filter v1.2.7");
    }

    public static Plugin getPluginInstance() {
        return plugin;
    }

    public static void createFilterFile() {
        File myObj;
        try {
            myObj = new File("plugins/Filter/messages.txt");
            if (!myObj.createNewFile()) {
                return;
            }

            System.out.println(ChatColor.GREEN + "[Filter] " + ChatColor.GRAY + "Database file not found. Created: " + myObj.getName());
        } catch (IOException var2) {
            System.out.println("An error occurred.");
            var2.printStackTrace();
        }

        try {
            myObj = new File("plugins/Filter/commands.txt");
            if (!myObj.createNewFile()) {
                return;
            }

            System.out.println(ChatColor.GREEN + "[Filter] " + ChatColor.GRAY + "Database file not found. Created: " + myObj.getName());
        } catch (IOException var1) {
            System.out.println("An error occurred.");
            var1.printStackTrace();
        }

    }
}