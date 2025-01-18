package com.venciti.proxy.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class BungeeMessageListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(message))) {
            String subchannel = in.readUTF();
            if (subchannel.equals("Message")) {
                String sender = in.readUTF();
                String msg = in.readUTF();

                Bukkit.broadcastMessage("§6[De " + sender + "]: §f" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
