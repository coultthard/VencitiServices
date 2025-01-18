package com.venciti.spigot.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.venciti.spigot.cmd.StaffChatCommand.STAFFCHAT;

public class StaffChatListener implements Listener {

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (STAFFCHAT.contains(player)) {
            event.setCancelled(true);
            String message = ChatColor.translateAlternateColorCodes('&', event.getMessage());

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.hasPermission("venciti.staffchat") || all.isOp() || all.hasPermission("venciti.chatstaff")) {
                    all.sendMessage("§e§lSTAFF-CHAT §7- §f" + player.getName() + ": §7" + message);
                }
            }
        }
    }
}
