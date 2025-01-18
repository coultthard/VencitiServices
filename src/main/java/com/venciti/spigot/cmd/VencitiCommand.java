package com.venciti.spigot.cmd;

import com.venciti.spigot.api.DownloadAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VencitiCommand extends Commands{

    protected VencitiCommand() {
        super("vencitiservices", "vs");
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

        if (args[0].equalsIgnoreCase("download")){
            DownloadAPI.update(player);
        }

    }



}
