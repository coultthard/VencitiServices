package com.venciti.proxy.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ReplyCommand extends Commands {
    public ReplyCommand() {
        super("reply", "r");
    }

    @Override
    public void perform(CommandSender sender, String[] args) throws SQLException {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("§cApenas jogadores podem usar este comando."));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length < 1) {
            player.sendMessage(new TextComponent("§cUso correto: /reply <mensagem>"));
            return;
        }

        HashMap<UUID, UUID> lastMessageSender = TellCommand.getLastMessageSender();
        UUID lastSenderUUID = lastMessageSender.get(player.getUniqueId());

        if (lastSenderUUID == null) {
            player.sendMessage(new TextComponent("§cNinguém enviou uma mensagem para você recentemente."));
            return;
        }

        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(lastSenderUUID);

        if (target == null || !target.isConnected()) {
            player.sendMessage(new TextComponent("§cO jogador para quem você está tentando responder não está mais online."));
            return;
        }

        String message = String.join(" ", args);

        target.sendMessage(new TextComponent("§6[De " + player.getName() + "]: §f" + message));
        player.sendMessage(new TextComponent("§6[Para " + target.getName() + "]: §f" + message));

        lastMessageSender.put(target.getUniqueId(), player.getUniqueId());
    }
}
