package com.venciti.spigot.listeners;

import com.venciti.spigot.api.AuthAPI;
import com.venciti.spigot.api.FakeAPI;
import com.venciti.spigot.api.skins.SkinAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.venciti.spigot.cmd.FakeCommand.SKIN_FAKE;

public class SkinsListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        String name = player.getName();
        String fakeName = FakeAPI.getFakeName(player);

        if (AuthAPI.isOriginal(player)){
            if (!SKIN_FAKE.contains(player)){
                SkinAPI.apply(player, name);
            } else {
                SkinAPI.apply(player, fakeName);
            }
        } else {
            if (!SKIN_FAKE.contains(player)){
                SkinAPI.apply(player, "chatao");
            } else {
                SkinAPI.apply(player, fakeName);
            }
        }
    }

}
