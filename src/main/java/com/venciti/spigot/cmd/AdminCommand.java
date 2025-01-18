package com.venciti.spigot.cmd;

import com.venciti.spigot.api.ActionBarAPI;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminCommand extends Commands {

    public static List<Player> ADMIN = new ArrayList<>();

    protected AdminCommand() {
        super("admin", "administrador");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("venciti.invsee") || !player.isOp()) {
            player.sendMessage("§cVocê não possui cargo o suficiente para executar este comando.");
            return;
        }

        if (sender instanceof Player) {
            if (args.length == 0) {
                if (ADMIN.contains(player)) {
                    ADMIN.remove(player);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.setAllowFlight(false);
                    ActionBarAPI.sendActionBar(player, "§cModo Admin desativado!");
                } else {
                    ADMIN.add(player);
                    player.setGameMode(GameMode.CREATIVE);
                    player.setAllowFlight(true);
                    ActionBarAPI.sendActionBar(player, "§aModo Admin ativado!");
                }
            }
        }
    }

}
