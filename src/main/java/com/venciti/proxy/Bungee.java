package com.venciti.proxy;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class Bungee extends Plugin {

    @Override
    public void onEnable() {
        preload();
    }

    @Override
    public void onDisable() {
        offload();
    }

    public static Bungee getInstance() {
        return (Bungee) ProxyServer.getInstance().getPluginManager().getPlugin("VencitiServices");
    }

    private void preload(){

    }

    private void offload(){

    }

}
