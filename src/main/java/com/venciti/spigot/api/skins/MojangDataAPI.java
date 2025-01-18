package com.venciti.spigot.api.skins;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MojangDataAPI {

    public static String[] getSkinData(String name) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name.trim());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() != 200) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
            String uuid = json.get("id").getAsString();

            reader.close();
            connection.disconnect();

            URL profileUrl = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            connection = (HttpURLConnection) profileUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() != 200) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            json = new JsonParser().parse(reader).getAsJsonObject();

            JsonObject properties = json.getAsJsonArray("properties").get(0).getAsJsonObject();
            return new String[]{properties.get("value").getAsString(), properties.get("signature").getAsString()};

        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
