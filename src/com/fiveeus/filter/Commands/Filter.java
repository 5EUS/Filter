package com.fiveeus.filter.Commands;

import com.fiveeus.filter.Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Filter implements CommandExecutor {
    Plugin plugin = Main.getPluginInstance();
    String prefix;
    String permission;

    public Filter() {
        this.prefix = this.plugin.getConfig().getString("prefix");
        this.permission = this.plugin.getConfig().getString("perm");
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission(this.permission)) {
                if (command.getName().equalsIgnoreCase("filter")) {
                    if (args.length >= 3) {
                        if (args[0].equals("command")) {
                            if (args[1].equals("add")) {
                                if (containsCommands(args[2])) {
                                    sender.sendMessage(this.prefix + "Commands.txt already contains this.");
                                } else {
                                    try {
                                        Files.write(Paths.get("plugins/Filter/commands.txt"), args[2].getBytes(), new OpenOption[]{StandardOpenOption.APPEND});
                                        Files.write(Paths.get("plugins/Filter/commands.txt"), "\n".getBytes(), new OpenOption[]{StandardOpenOption.APPEND});
                                        sender.sendMessage(this.prefix + "Filtered command successfully added.");
                                    } catch (IOException var7) {
                                    }
                                }
                            } else if (args[1].contains("remove")) {
                                if (!containsCommands(args[2])) {
                                    sender.sendMessage(this.prefix + "Not a blocked command!");
                                } else {
                                    deleteString(args[2], "plugins/Filter/commands.txt");
                                    sender.sendMessage(this.prefix + "Command removed from filter.");
                                }
                            } else {
                                sender.sendMessage("§e§lHELP MENU");
                                sender.sendMessage("§7/filter §bShows this help menu");
                                sender.sendMessage("§7/filter §bcommand <add/remove> <command> §b(Without /)");
                                sender.sendMessage("§7/filter §bmessage <add/remove> <message>");
                            }
                        } else if (args[0].equals("message")) {
                            if (args[1].contains("add")) {
                                if (containsMessage(args[2])) {
                                    sender.sendMessage(this.prefix + "Messages.txt already contains this.");
                                } else {
                                    try {
                                        Files.write(Paths.get("plugins/Filter/messages.txt"), args[2].getBytes(), new OpenOption[]{StandardOpenOption.APPEND});
                                        Files.write(Paths.get("plugins/Filter/messages.txt"), "\n".getBytes(), new OpenOption[]{StandardOpenOption.APPEND});
                                        sender.sendMessage(this.prefix + "Filtered message successfully added.");
                                    } catch (IOException var6) {
                                    }
                                }
                            } else if (args[1].contains("remove")) {
                                if (!containsMessage(args[2])) {
                                    sender.sendMessage(this.prefix + "Not a blocked message!");
                                } else {
                                    deleteString(args[2], "plugins/Filter/messages.txt");
                                    sender.sendMessage(this.prefix + "Message removed from filter.");
                                }
                            } else {
                                sender.sendMessage("§e§lHELP MENU");
                                sender.sendMessage("§7/filter §bShows this help menu");
                                sender.sendMessage("§7/filter §bcommand <add/remove> <command> §b(Without /)");
                                sender.sendMessage("§7/filter §bmessage <add/remove> <message>");
                            }
                        } else {
                            sender.sendMessage(this.prefix + "Invalid subcommand.");
                        }
                    } else {
                        sender.sendMessage("§e§lHELP MENU");
                        sender.sendMessage("§7/filter §bShows this help menu");
                        sender.sendMessage("§7/filter §bcommand <add/remove> <command> §b(Without /)");
                        sender.sendMessage("§7/filter §bmessage <add/remove> <message>");
                    }
                }
            } else {
                sender.sendMessage(this.prefix + "Insufficient permissions.");
            }
        } else {
            sender.sendMessage(this.prefix + "This command can only be executed by a player.");
        }

        return true;
    }

    public static boolean containsMessage(String arg) {
        File file = new File("plugins/Filter/messages.txt");

        try {
            Scanner scanner = new Scanner(file);
            int var3 = 0;

            String line;
            do {
                if (!scanner.hasNextLine()) {
                    return false;
                }

                line = scanner.nextLine();
                ++var3;
            } while(!line.equals(arg));

            return true;
        } catch (FileNotFoundException var5) {
            return false;
        }
    }

    public static boolean containsCommands(String arg) {
        File file = new File("plugins/Filter/commands.txt");

        try {
            Scanner scanner = new Scanner(file);
            int var3 = 0;

            String line;
            do {
                if (!scanner.hasNextLine()) {
                    return false;
                }

                line = scanner.nextLine();
                ++var3;
            } while(!line.equals(arg));

            return true;
        } catch (FileNotFoundException var5) {
            return false;
        }
    }

    private static void deleteString(String string, String path) {
        File file = new File(path);

        try {
            File temp = File.createTempFile("file", ".txt", file.getParentFile());
            String charset = "UTF-8";
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));

            PrintWriter writer;
            String line;
            for(writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset)); (line = reader.readLine()) != null; line = line.replace(line, "")) {
            }

            reader.close();
            writer.close();
            file.delete();
            temp.renameTo(file);
        } catch (IOException var8) {
            var8.printStackTrace();
        }

    }
}
