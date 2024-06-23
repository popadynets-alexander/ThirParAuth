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
            executeSubcommandsForUnknownSenderType();
        }
    }

    private void sendHelp()
    {
        throw new UnsupportedOperationException("Unimplemented method 'sendHelp'");
    }

    private void executePlayerSubcommands()
    {
        String subcommand = arg(0);
        switch (subcommand)
        {
            case "help":
                sendHelp();
                break;

            case "set-password":
                break;

            case "enable":
                break;

            case "disable":
                break;

            case "terminate":
                break;

            case "begin-trust-ip":
                break;

            case "begin-distrust-ip":
                break;

            case "forget-ip":
                break;

            case "inspect-ips":
                break;

            case "lockdown":
            case "lockdown-end":
            case "generate-password-for":
                sender.sendMessage("§4You must be the console to use these commands.");
                sender.sendMessage("§6Try §e/"+alias+" help");
                break;
            
            default:
                sender.sendMessage("§4Unknown subcommand '§c"+subcommand+"§4'");
                sender.sendMessage("§6Try §e/"+alias+" help");
                break;
        }
    }

    private void executeConsoleSubcommands()
    {
        String subcommand = arg(0);
        switch (subcommand)
        {
            case "help":
                sendHelp();
                break;

            case "lockdown":
                break;

            case "lockdown-end":
                break;

            case "generate-password-for":
                break;

            case "set-password":
            case "enable":
            case "disable":
            case "terminate":
            case "begin-trust-ip":
            case "begin-distrust-ip":
            case "forget-ip":
            case "inspect-ips":
                sender.sendMessage("§4You must be a player to use this subcommand.");
                sender.sendMessage("§6Try §e/"+alias+" help");
                break;

            default:
                sender.sendMessage("§4Unknown subcommand '§c"+subcommand+"§4'");
                sender.sendMessage("§6Try §e/"+alias+" help");
                break;
        }
    }

    private void executeSubcommandsForUnknownSenderType()
    {
        String subcommand = arg(0);
        switch (subcommand)
        {
            case "help":
                sendHelp();
                break;

            case "set-password":
            case "enable":
            case "disable":
            case "terminate":
            case "begin-trust-ip":
            case "begin-distrust-ip":
            case "forget-ip":
            case "inspect-ips":
                sender.sendMessage("§4You must be a player to use this subcommand.");
                sender.sendMessage("§6Try §e/"+alias+" help");
                break;

            case "lockdown":
            case "lockdown-end":
            case "generate-password-for":
                sender.sendMessage("§4You must be the console to use these commands.");
                sender.sendMessage("§6Try §e/"+alias+" help");
                break;

            default:
                sender.sendMessage("§4Unknown subcommand '§c"+subcommand+"§4'");
                sender.sendMessage("§6Try §e/"+alias+" help");
                break;
        }
    }
}
