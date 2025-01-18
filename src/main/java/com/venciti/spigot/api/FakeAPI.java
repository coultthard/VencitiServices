package com.venciti.spigot.api;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("CallToPrintStackTrace")
public class FakeAPI {

    private static final Map<String, String> fakeNames = new HashMap<>();
    private static final Map<String, String> originalNames = new HashMap<>();

    public static void apply(Player player, String newName) {
        try {
            String originalName = player.getName();
            if (!originalNames.containsKey(player.getUniqueId().toString())) {
                originalNames.put(player.getUniqueId().toString(), originalName);
            }

            fakeNames.put(player.getUniqueId().toString(), newName);

            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

            GameProfile profile = nmsPlayer.getProfile();

            Field nameField = GameProfile.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(profile, newName);

            refresh(player);

        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("§cOcorreu um erro ao alterar seu nick.");
        }
    }

    public static void reset(Player player) {
        try {
            String originalName = originalNames.get(player.getUniqueId().toString());

            if (originalName == null) {
                return;
            }

            fakeNames.remove(player.getUniqueId().toString());

            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

            GameProfile profile = nmsPlayer.getProfile();

            Field nameField = GameProfile.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(profile, originalName);

            refresh(player);

        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("§cOcorreu um erro ao restaurar seu nick.");
        }
    }

    private static void refresh(Player player) {
        PacketPlayOutPlayerInfo removePacket = new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                ((CraftPlayer) player).getHandle()
        );
        PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                ((CraftPlayer) player).getHandle()
        );

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) onlinePlayer).getHandle().playerConnection.sendPacket(removePacket);
            ((CraftPlayer) onlinePlayer).getHandle().playerConnection.sendPacket(addPacket);
        }
    }

    public static boolean isUsingFakeName(Player player) {
        return fakeNames.containsKey(player.getUniqueId().toString());
    }

    public static String getFakeName(Player player) {
        return fakeNames.get(player.getUniqueId().toString());
    }

}
