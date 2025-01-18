package com.venciti.spigot.api.skins;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static com.venciti.spigot.cmd.FlyCommand.FLY;

@SuppressWarnings({"CallToPrintStackTrace", "deprecation"})
public class SkinAPI {

    private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static void apply(Player player, String skinName) {
        try {
            String[] skinData = MojangDataAPI.getSkinData(skinName);
            if (skinData == null) {
                reset(player);
                return;
            }

            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftPlayer");
            Class<?> entityPlayerClass = Class.forName("net.minecraft.server." + VERSION + ".EntityPlayer");
            Class<?> propertyClass = Class.forName("com.mojang.authlib.properties.Property");

            Object craftPlayer = craftPlayerClass.cast(player);
            Method getHandle = craftPlayerClass.getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(craftPlayer);
            Method getProfile = entityPlayerClass.getMethod("getProfile");
            Object gameProfile = getProfile.invoke(entityPlayer);

            Method getProperties = gameProfile.getClass().getMethod("getProperties");
            Object properties = getProperties.invoke(gameProfile);
            Method removeProperty = properties.getClass().getMethod("removeAll", Object.class);
            removeProperty.invoke(properties, "textures");

            Constructor<?> propertyConstructor = propertyClass.getConstructor(String.class, String.class, String.class);
            Object property = propertyConstructor.newInstance("textures", skinData[0], skinData[1]);

            Method putProperty = properties.getClass().getMethod("put", Object.class, Object.class);
            putProperty.invoke(properties, "textures", property);

            update(player);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("§cOcorreu um erro ao atualizar a skin!");
        }
    }

    public static void reset(Player player) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftPlayer");
            Class<?> entityPlayerClass = Class.forName("net.minecraft.server." + VERSION + ".EntityPlayer");

            Object craftPlayer = craftPlayerClass.cast(player);
            Method getHandle = craftPlayerClass.getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(craftPlayer);
            Method getProfile = entityPlayerClass.getMethod("getProfile");
            Object gameProfile = getProfile.invoke(entityPlayer);

            Method getProperties = gameProfile.getClass().getMethod("getProperties");
            Object properties = getProperties.invoke(gameProfile);
            Method removeProperty = properties.getClass().getMethod("removeAll", Object.class);
            removeProperty.invoke(properties, "textures");

            update(player);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("§cOcorreu um erro ao resetar a skin para o padrão!");
        }
    }

    private static void update(Player player) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.CraftPlayer");
            Class<?> packetPlayOutPlayerInfoClass = Class.forName("net.minecraft.server." + VERSION + ".PacketPlayOutPlayerInfo");
            Class<?> packetPlayOutRespawnClass = Class.forName("net.minecraft.server." + VERSION + ".PacketPlayOutRespawn");
            Class<?> enumPlayerInfoActionClass = Class.forName("net.minecraft.server." + VERSION + ".PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
            Class<?> entityPlayerArrayClass = Class.forName("[Lnet.minecraft.server." + VERSION + ".EntityPlayer;");
            Class<?> enumDifficultyClass = Class.forName("net.minecraft.server." + VERSION + ".EnumDifficulty");
            Class<?> worldTypeClass = Class.forName("net.minecraft.server." + VERSION + ".WorldType");

            Object craftPlayer = craftPlayerClass.cast(player);
            Method getHandle = craftPlayerClass.getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(craftPlayer);

            Object playerArray = java.lang.reflect.Array.newInstance(Class.forName("net.minecraft.server." + VERSION + ".EntityPlayer"), 1);
            java.lang.reflect.Array.set(playerArray, 0, entityPlayer);

            Constructor<?> infoConstructor = packetPlayOutPlayerInfoClass.getConstructor(enumPlayerInfoActionClass, entityPlayerArrayClass);
            Object removePlayer = infoConstructor.newInstance(enumPlayerInfoActionClass.getField("REMOVE_PLAYER").get(null), playerArray);
            Object addPlayer = infoConstructor.newInstance(enumPlayerInfoActionClass.getField("ADD_PLAYER").get(null), playerArray);

            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.equals(player)) {
                    online.hidePlayer(player);
                    Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugins()[0], () ->
                            online.showPlayer(player), 2L);
                }
            }

            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugins()[0], () -> {
                int chunkRadius = 4;
                for (int x = -chunkRadius; x <= chunkRadius; x++) {
                    for (int z = -chunkRadius; z <= chunkRadius; z++) {
                        player.getWorld().refreshChunk(
                                player.getLocation().getChunk().getX() + x,
                                player.getLocation().getChunk().getZ() + z
                        );
                    }
                }
            }, 5L);

            Object worldServer = entityPlayer.getClass().getField("world").get(entityPlayer);
            int dimension = (int) entityPlayer.getClass().getField("dimension").get(entityPlayer);
            Object difficulty = worldServer.getClass().getMethod("getDifficulty").invoke(worldServer);

            Object worldType;
            try {
                worldType = worldTypeClass.getField("NORMAL").get(null);
            } catch (NoSuchFieldException e) {
                worldType = worldTypeClass.getEnumConstants()[0];
            }

            Constructor<?> respawnConstructor = packetPlayOutRespawnClass.getConstructor(
                    Integer.TYPE,
                    enumDifficultyClass,
                    worldTypeClass,
                    Class.forName("net.minecraft.server." + VERSION + ".WorldSettings$EnumGamemode")
            );

            Object respawn = respawnConstructor.newInstance(
                    dimension,
                    difficulty,
                    worldType,
                    Class.forName("net.minecraft.server." + VERSION + ".WorldSettings$EnumGamemode")
                            .getMethod("getById", int.class)
                            .invoke(null, player.getGameMode().getValue())
            );

            for (Player online : Bukkit.getOnlinePlayers()) {
                Object onlinePlayer = craftPlayerClass.cast(online);
                Object entityOnlinePlayer = getHandle.invoke(onlinePlayer);
                Object connection = entityOnlinePlayer.getClass().getField("playerConnection").get(entityOnlinePlayer);

                Method sendPacket = connection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + VERSION + ".Packet"));
                sendPacket.invoke(connection, removePlayer);
                sendPacket.invoke(connection, addPlayer);

                if (online.equals(player)) {
                    sendPacket.invoke(connection, respawn);
                    online.updateInventory();
                }
            }

            Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugins()[0], () ->
                    player.teleport(player.getLocation())
            );

            if (FLY.contains(player)) {
                player.setAllowFlight(true);
            } else {
                player.setAllowFlight(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
