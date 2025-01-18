package com.venciti.spigot.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.InvocationTargetException;

public abstract class Commands extends Command {

    @Override
    public boolean execute(CommandSender sender, String cmdLabel, String[] args) {
        this.perform(sender, cmdLabel, args);
        return true;
    }

    public abstract void perform(CommandSender sender, String label, String[] args);

    protected Commands(String name, String... aliases) {
        super(name);
        try {
            SimpleCommandMap scm = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
            scm.register("VencitiServices", this); // Registers the main command

            if (aliases != null && aliases.length > 0) {
                for (String alias : aliases) {
                    scm.register(alias, "vencitiservices", this);
                }
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setup() {
        new GameModeCommand();
        new FlyCommand();
        new InvseeCommand();
        new OnlineCommand();
        new PingCommand();
        new StaffChatCommand();
        new AdminCommand();
        new TellCommand();
        new ReplyCommand();
        new FakeCommand();
        new VencitiServicesCommand();
    }

}
