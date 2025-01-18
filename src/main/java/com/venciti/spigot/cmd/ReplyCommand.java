package com.venciti.spigot.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ReplyCommand extends Commands {

    protected ReplyCommand() {
        super("reply", "r");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("§a");
            player.sendMessage(" §eComo usar o Reply?");
            player.sendMessage(" §f/rely ou r (mensagem)");
            player.sendMessage("§a");
            return;
        }

        HashMap<UUID, UUID> lastMessageSender = TellCommand.getLastMessageSender();
        UUID lastSenderUUID = lastMessageSender.get(player.getUniqueId());

        if (lastSenderUUID == null) {
            player.sendMessage("§cNinguém enviou uma mensagem para você recentemente.");
            return;
        }

        Player target = Bukkit.getPlayer(lastSenderUUID);

        if (target == null || !target.isOnline()) {
            player.sendMessage("§cO jogador para quem você está tentando responder não está mais online.");
            return;
        }

        String message = String.join(" ", args);

        target.sendMessage("§e[De " + player.getName() + "]: " + "§f" + message);
        player.sendMessage("§e[Para " + target.getName() + "]: " + "§f" + message);

        lastMessageSender.put(target.getUniqueId(), player.getUniqueId());

    }

}
