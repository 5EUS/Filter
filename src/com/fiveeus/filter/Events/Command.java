package com.fiveeus.filter.Events;

import com.fiveeus.filter.Main;
import com.fiveeus.filter.Commands.Filter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

public class Command implements Listener {
    Plugin plugin = Main.getPluginInstance();
    String prefix;
    String permission;

    public Command() {
        this.prefix = this.plugin.getConfig().getString("prefix");
        this.permission = this.plugin.getConfig().getString("perm");
    }

    @EventHandler
    public void onPlayerSendCommand(PlayerCommandPreprocessEvent e) throws InterruptedException {
        Player player = e.getPlayer();
        String command = e.getMessage().substring(1);
        if (Filter.containsCommands(command)) {
            e.setCancelled(true);
            player.sendMessage(this.prefix + "Command blocked!");
        }

    }
}