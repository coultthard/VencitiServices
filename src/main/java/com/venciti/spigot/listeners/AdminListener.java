package com.venciti.spigot.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import static com.venciti.spigot.cmd.AdminCommand.ADMIN;

public class AdminListener implements Listener {

    @EventHandler
    public void amdin(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Player target = (Player) event.getRightClicked();

        if (ADMIN.contains(player)) {
            player.openInventory(target.getInventory());
        }
    }

}
