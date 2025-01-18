package com.venciti.spigot.cmd;

import com.venciti.spigot.api.ActionBarAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand extends Commands {

    public static List<Player> FLY = new ArrayList<>();

    protected FlyCommand() {
        super("fly", "voar");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("venciti.gamemode") || !player.isOp()) {
                player.sendMessage("§cVocê não possui cargo o suficiente para executar este comando.");
                return;
            }

            if (args.length == 0) {
                if (player.getAllowFlight() == true) {
                    if (FLY.contains(player)) {
                        FLY.remove(player);
                    }
                    player.setAllowFlight(false);
                    ActionBarAPI.sendActionBar(player, "§eModo de voo desativado.");
                } else {
                    if (!FLY.contains(player)) {
                        FLY.add(player);
                    }
                    player.setAllowFlight(true);
                    ActionBarAPI.sendActionBar(player, "§aModo de voo ativado.");
                }
            }
        }
    }

}
