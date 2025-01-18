package com.venciti.proxy.listener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static com.venciti.proxy.cmd.StaffChatCommand.STAFFCHAT;

public class StaffChatListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (STAFFCHAT.contains(player)) {
            event.setCancelled(true);

            String message = event.getMessage();

            for (ProxiedPlayer onlinePlayer : ProxyServer.getInstance().getPlayers()) {
                if (onlinePlayer.hasPermission("venciti.staffchat")) {
                    onlinePlayer.sendMessage(new TextComponent(
                            "§e§lSTAFF-CHAT §7- §f" + player.getName() + ": §7" + message
                    ));
                }
            }
        }
    }

}
