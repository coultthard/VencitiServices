package com.venciti.proxy.listener;

import com.venciti.proxy.Bungee;

public class Listeners {

    public static void setup() {
        Bungee.getInstance().getProxy().getPluginManager().registerListener(Bungee.getInstance(), new StaffChatListener());
    }

}
