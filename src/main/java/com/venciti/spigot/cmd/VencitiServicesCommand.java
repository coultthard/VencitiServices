package com.venciti.spigot.cmd;

import com.venciti.spigot.Services;
import com.venciti.spigot.api.DownloadAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VencitiServicesCommand extends Commands{

    protected VencitiServicesCommand() {
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

        String autors = Services.getInstance().getDescription().getAuthors().toString();
        if (args.length == 0){
            player.sendMessage("§e");
            player.sendMessage(" §eVencitiServirces");
            player.sendMessage(" §fCriador: §7" + autors);
            player.sendMessage("§e");
        }

        if (args[0].equalsIgnoreCase("download")){
            DownloadAPI.update(player);
        }

    }



}
