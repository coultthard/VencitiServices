package com.venciti.proxy.cmd;

import com.venciti.proxy.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class TellCommand extends Commands {

    private static final HashMap<UUID, UUID> lastMessageSender = new HashMap<>();

    public TellCommand() {
        super("tell", "t");
    }

    @Override
    public void perform(CommandSender sender, String[] args) throws SQLException {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("§cApenas jogadores podem usar este comando."));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length < 2) {
            player.sendMessage(new TextComponent("§cUso correto: /tell <jogador> <mensagem>"));
            return;
        }

        ProxiedPlayer target = Bungee.getInstance().getProxy().getPlayer(args[0]);

        if (target == null || !target.isConnected()) {
            player.sendMessage(new TextComponent("§cJogador não encontrado ou está offline."));
            return;
        }

        String message = String.join(" ", args).substring(args[0].length() + 1);

        target.sendMessage(new TextComponent("§6[De " + player.getName() + "]: §f" + message));
        player.sendMessage(new TextComponent("§6[Para " + target.getName() + "]: §f" + message));

        lastMessageSender.put(target.getUniqueId(), player.getUniqueId());
    }

    public static HashMap<UUID, UUID> getLastMessageSender() {
        return lastMessageSender;
    }

}
