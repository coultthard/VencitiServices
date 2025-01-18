package com.venciti.spigot.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand extends Commands {

    protected InvseeCommand() {
        super("invsee", "inv");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("venciti.invsee") || !player.isOp()) {
                player.sendMessage("§cVocê não possui cargo o suficiente para executar este comando.");
                return;
            }

            if (args.length == 0) {
                player.sendMessage("§a");
                player.sendMessage(" §eComo usar o Invsee?");
                player.sendMessage(" §f/invsee (player)");
                player.sendMessage("§a");
                return;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage("§cNão encontramos este jogador ou ele está offline.");
                return;
            }

            player.openInventory(target.getInventory());
        }
    }

}
