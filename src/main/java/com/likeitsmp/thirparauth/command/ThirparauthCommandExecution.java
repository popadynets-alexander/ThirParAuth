package com.likeitsmp.thirparauth.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.likeitsmp.commands.CommandExecution;

public final class ThirparauthCommandExecution extends CommandExecution
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

    private ThirparauthCommandExecution(CommandSender sender, Command command, String alias, String[] rawArgs)
    {
        super(sender, command, alias, rawArgs);

        if (argsCount() == 0)
        {
            sender.sendMessage("§6Try using §e/"+alias+" help");
            return;
        }

        if (sender instanceof Player)
        {
            executePlayerSubcommands();
        }
        else if (sender instanceof ConsoleCommandSender)
        {
            executeConsoleSubcommands();
        }
        else
        {
            sender.sendMessage("§4You must be either a player or the console to use this command");
        }
    }

    private void executePlayerSubcommands()
    {
        throw new UnsupportedOperationException("Unimplemented method 'executePlayerSubcommands'");
    }
    
    private void executeConsoleSubcommands()
    {
        throw new UnsupportedOperationException("Unimplemented method 'executeConsoleSubcommands'");
    }
}
