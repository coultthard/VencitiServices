package com.venciti.spigot.cmd;

import com.venciti.spigot.api.ActionBarAPI;
import com.venciti.spigot.api.FakeAPI;
import com.venciti.spigot.api.skins.SkinAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FakeCommand extends Commands {

    public static List<Player> SKIN_FAKE = new ArrayList<>();

    protected FakeCommand() {
        super("fake");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("venciti.fake") || !player.isOp()) {
            player.sendMessage("§cVocê não possui cargo o suficiente para executar este comando.");
            return;
        }

        if (args.length < 1) {
            player.sendMessage("§a");
            player.sendMessage(" §eComo usar o Fake?");
            player.sendMessage(" §f/fake (nick) ou (reset)");
            player.sendMessage("§a");
            return;
        }

        if (args[0].equalsIgnoreCase("reset")) {
            if (SKIN_FAKE.contains(player)){
                SKIN_FAKE.remove(player);
            }
            FakeAPI.reset(player);
            SkinAPI.apply(player, player.getName());
            ActionBarAPI.sendActionBar(player, "§aNick resetado para: §f" + player.getName());
            return;
        }

        String newName = args[0];
        if (newName.length() > 16) {
            player.sendMessage("§cO nome não pode ter mais de 16 caracteres.");
            return;
        }

        if (newName.equals(player.getName())) {
            player.sendMessage("§cVocê não pode usar seu próprio nick como fake.");
        }

        if (!SKIN_FAKE.contains(player)){
            SKIN_FAKE.add(player);
        }

        SkinAPI.apply(player, newName);
        FakeAPI.apply(player, newName);

        ActionBarAPI.sendActionBar(player, "§aNick alterado para: §f" + newName);
    }


}
