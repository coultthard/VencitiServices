package com.venciti.spigot.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OnlineCommand extends Commands {

    protected OnlineCommand() {
        super("online", "on");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Bukkit.getOnlinePlayers().size() == 1) {
                player.sendMessage("§aHá apenas §f" + Bukkit.getOnlinePlayers().size() + " §aJogador online no momento.");
            } else {
                player.sendMessage("§aHá §f" + Bukkit.getOnlinePlayers().size() + " §aJogadores online no momento.");
            }
        }
    }

}
