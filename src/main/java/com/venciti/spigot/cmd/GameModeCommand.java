package com.venciti.spigot.cmd;

import com.venciti.spigot.api.ActionBarAPI;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand extends Commands {

    protected GameModeCommand() {
        super("gamemode", "gm");
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
                player.sendMessage("§a");
                player.sendMessage(" §eComo usar o Gamemode?");
                player.sendMessage(" §f/gamemode ou gm (0 ou 1 ou 2 ou 3)");
                player.sendMessage("§a");
                return;
            }

            if (args[0].equalsIgnoreCase("0")) {
                player.setGameMode(GameMode.SURVIVAL);
                ActionBarAPI.sendActionBar(player, "§aModo de jogo alterado para: §f" + player.getGameMode().name());
            } else if (args[0].equalsIgnoreCase("1")) {
                player.setGameMode(GameMode.CREATIVE);
                ActionBarAPI.sendActionBar(player, "§aModo de jogo alterado para: §f" + player.getGameMode().name());
            } else if (args[0].equalsIgnoreCase("2")) {
                player.setGameMode(GameMode.ADVENTURE);
                ActionBarAPI.sendActionBar(player, "§aModo de jogo alterado para: §f" + player.getGameMode().name());
            } else if (args[0].equalsIgnoreCase("3")) {
                player.setGameMode(GameMode.SPECTATOR);
                ActionBarAPI.sendActionBar(player, "§aModo de jogo alterado para: §f" + player.getGameMode().name());
            } else {
                player.sendMessage("§cModo de jogo não encontrado.");
            }
        }
    }

}
