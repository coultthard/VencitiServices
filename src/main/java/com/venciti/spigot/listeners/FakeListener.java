package com.venciti.spigot.listeners;

import com.venciti.spigot.api.FakeAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class FakeListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (FakeAPI.isUsingFakeName(player)) {
            event.setFormat(" §f" + FakeAPI.getFakeName(player) + ": §7" + event.getMessage());
        } else {
            event.setFormat(" §f" + player.getName() + ": §7" + event.getMessage());
        }
    }

}
