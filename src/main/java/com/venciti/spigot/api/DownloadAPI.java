package com.venciti.spigot.api;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadAPI {

    public static void update(Player player) {
        String downloadUrl = "https://github.com/SEU_USUARIO/SEU_REPOSITORIO/releases/download/v1.0/SEU_PLUGIN.jar";
        File pluginsFolder = new File(player.getServer().getWorldContainer(), "plugins");
        File saveDir = new File(pluginsFolder, "SEU_PLUGIN.jar");

        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            try (InputStream in = connection.getInputStream();
                 FileOutputStream out = new FileOutputStream(saveDir)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                player.getServer().getPluginManager().disablePlugin(player.getServer().getPluginManager().getPlugin("VencitiServices"));
                player.getServer().getPluginManager().enablePlugin(player.getServer().getPluginManager().getPlugin("VencitiServices"));
            }
        } catch (IOException e) {
            player.sendMessage("Erro ao tentar baixar o plugin: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
