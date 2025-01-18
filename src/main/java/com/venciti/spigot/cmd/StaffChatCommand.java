package com.venciti.spigot.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StaffChatCommand extends Commands {

    public static final List<Player> STAFFCHAT = new ArrayList<>();

    protected StaffChatCommand() {
        super("staffchat", "sc");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("venciti.staffchat") || !player.isOp()) {
            player.sendMessage("§cVocê não possui cargo o suficiente para executar este comando.");
            return;
        }

        if (sender instanceof Player) {
            if (args.length == 0) {
                if (!STAFFCHAT.contains(player)) {
                    STAFFCHAT.add(player);
                    player.sendMessage("§aChat Staff habilitado");
                } else {
                    STAFFCHAT.remove(player);
                    player.sendMessage("§cChat Staff desabilitado");
                }
            }
        }
    }


}
