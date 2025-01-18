package com.venciti.spigot.listeners;

import com.venciti.DeleteClass;
import com.venciti.spigot.Services;
import org.bukkit.Bukkit;

public class Listeners {

    public static void setup() {
        Bukkit.getPluginManager().registerEvents(new StaffChatListener(), Services.getInstance());
        Bukkit.getPluginManager().registerEvents(new AdminListener(), Services.getInstance());
        Bukkit.getPluginManager().registerEvents(new FakeListener(), Services.getInstance());
        Bukkit.getPluginManager().registerEvents(new SkinsListener(), Services.getInstance());

        Bukkit.getPluginManager().registerEvents(new DeleteClass(), Services.getInstance());
    }

}
