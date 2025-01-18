package com.venciti.spigot.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TellCommand extends Commands {

    private static final HashMap<UUID, UUID> lastMessageSender = new HashMap<>();

    protected TellCommand() {
        super("tell", "t");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("§a");
            player.sendMessage(" §eComo usar o Tell?");
            player.sendMessage(" §f/tell ou t (jogador) (mensagem)");
            player.sendMessage("§a");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
            return;
        }

        String message = String.join(" ", args).substring(args[0].length() + 1);

        target.sendMessage(ChatColor.GOLD + "[De " + player.getName() + "]: " + ChatColor.WHITE + message);
        player.sendMessage(ChatColor.GOLD + "[Para " + target.getName() + "]: " + ChatColor.WHITE + message);

        lastMessageSender.put(target.getUniqueId(), player.getUniqueId());

    }

    public static HashMap<UUID, UUID> getLastMessageSender() {
        return lastMessageSender;
    }

}
