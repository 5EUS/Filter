package com.fiveeus.filter.Events;

import com.fiveeus.filter.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class Chat implements Listener {
    Plugin plugin = Main.getPluginInstance();
    String prefix;
    String permission;

    public Chat() {
        this.prefix = this.plugin.getConfig().getString("prefix");
        this.permission = this.plugin.getConfig().getString("perm");
    }

    @EventHandler
    public void onPlayerSendMessage(AsyncPlayerChatEvent e) throws InterruptedException {
        Player player = e.getPlayer();
        String message = e.getMessage();
        File file = new File("plugins/Filter/messages.txt");

        try {
            Scanner scanner = new Scanner(file);
            int var6 = 0;

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ++var6;
                if (containsIgnoreCase(message, line)) {
                    e.setCancelled(true);
                    player.sendMessage(this.prefix + "No naughty words!");
                }
            }
        } catch (FileNotFoundException var8) {
            Main.createFilterFile();
        }

    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str != null && searchStr != null) {
            int length = searchStr.length();
            if (length == 0) {
                return true;
            } else {
                for(int i = str.length() - length; i >= 0; --i) {
                    if (str.regionMatches(true, i, searchStr, 0, length)) {
                        return true;
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }
}