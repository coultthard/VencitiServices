package com.venciti;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeleteClass implements Listener {

    @EventHandler
    public void delete(PlayerJoinEvent e){
        Player player = e.getPlayer();
        e.setJoinMessage(null);

        player.setHealth(20);
        player.setFoodLevel(20);
    }

    @EventHandler
    public void deleteOne(EntityDamageEvent e){
        Entity entity = e.getEntity();
        if (entity instanceof Player){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void deleteTwo(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

}
