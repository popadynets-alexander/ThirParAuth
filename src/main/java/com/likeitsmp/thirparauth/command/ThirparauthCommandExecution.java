package com.likeitsmp.thirparauth.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class ThirparauthCommandExecution
{
    private static final boolean DO_NOT_SEND_COMMAND_USAGE_BACK = true;

    public static void initExecutor()
    {
        Bukkit.getPluginCommand("3pa").setExecutor(
            (sender, command, alias, args) ->
            {
                new ThirparauthCommandExecution(sender, command, alias, args);
                return DO_NOT_SEND_COMMAND_USAGE_BACK;
            }
        );
    }

    private ThirparauthCommandExecution(CommandSender sender, Command command, String alias, String[] args)
    {
        throw new UnsupportedOperationException("Not Implemented ThirparauthCommandExecution");
    }
}
