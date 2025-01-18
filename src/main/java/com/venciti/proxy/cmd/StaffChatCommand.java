package com.venciti.proxy.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class StaffChatCommand extends Commands {

    public static final Set<ProxiedPlayer> STAFFCHAT = new HashSet<>();

    public StaffChatCommand() {
        super("staffchat", "sc");
    }

    @Override
    public void perform(CommandSender sender, String[] args) throws SQLException {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("§cApenas jogadores podem executar este comando."));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!player.hasPermission("venciti.staffchat")) {
            player.sendMessage(new TextComponent("§cVocê não possui permissão para executar este comando."));
            return;
        }

        if (args.length == 0) {
            if (!STAFFCHAT.contains(player)) {
                STAFFCHAT.add(player);
                player.sendMessage(new TextComponent("§aChat Staff habilitado."));
            } else {
                STAFFCHAT.remove(player);
                player.sendMessage(new TextComponent("§cChat Staff desabilitado."));
            }
        } else {
            player.sendMessage(new TextComponent("§cUso incorreto. Utilize: /staffchat"));
        }
    }
}
