package com.venciti.proxy.cmd;

import com.venciti.proxy.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public abstract class Commands extends Command {

    public Commands(String name, String... aliases) {
        super(name, null, aliases);
        Bungee.getInstance().getProxy().getPluginManager().registerCommand(Bungee.getInstance(), this);
    }

    public abstract void perform(CommandSender sender, String[] args) throws SQLException;

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            String serverNome = p.getServer().getInfo().getName();
            if (serverNome.equalsIgnoreCase("login")) {
                p.sendMessage(new TextComponent("§cVoce deve se autenticar para fazer isto."));
            } else {
                try {
                    this.perform(sender, args);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            try {
                this.perform(sender, args);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void setup() {
        new TellCommand();
        new ReplyCommand();
        new StaffChatCommand();
    }

}
