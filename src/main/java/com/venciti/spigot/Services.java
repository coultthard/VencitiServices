package com.venciti.spigot;

import com.venciti.spigot.cmd.Commands;
import com.venciti.spigot.listeners.Listeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class Services extends JavaPlugin {

    @Override
    public void onEnable() {
        preload();
    }

    @Override
    public void onDisable() {
        offload();
    }

    public static Services getInstance() {
        return Services.getPlugin(Services.class);
    }

    private void preload() {
        Commands.setup();
        Listeners.setup();
    }

    private void offload() {

    }

}
