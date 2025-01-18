package com.venciti.spigot.api;

import org.bukkit.entity.Player;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthAPI {

    /**
     * Verifica se o UUID do jogador corresponde ao UUID da Mojang.
     * @param player jogador
     * @return true se for original, false caso contrário
     */
    public static boolean isOriginal(Player player) {
        try {
            String playerName = player.getName();

            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
