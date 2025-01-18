package com.venciti.spigot.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand extends Commands {

    protected PingCommand() {
        super("ping", "ms");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage("§aSeu ping é de §f" + ((CraftPlayer) player).getHandle().ping + " §ams!");
                return;
            }

            if (!player.hasPermission("venciti.invsee") || !player.isOp()) {
                player.sendMessage("§cVocê não possui cargo o suficiente para executar este comando.");
                return;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage("§cNão encontramos este jogador ou ele está offline.");
                return;
            }
            player.sendMessage("§aO ping de §f" + target.getName() + "§a é de §f" + ((CraftPlayer) target).getHandle().ping + " §ams!");
        }
    }

}
