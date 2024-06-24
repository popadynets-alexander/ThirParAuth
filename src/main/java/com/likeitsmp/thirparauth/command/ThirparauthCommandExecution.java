package com.likeitsmp.thirparauth.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

import com.likeitsmp.commands.Args;
import com.likeitsmp.commands.CommandExecution;
import com.likeitsmp.console.Console;
import com.likeitsmp.thirparauth.data.UserData;
import com.likeitsmp.thirparauth.data.UserDatabase;
import com.likeitsmp.thirparauth.processes.TextInputProcess;

public final class ThirparauthCommandExecution extends CommandExecution
{
    private static final boolean DO_NOT_SEND_COMMAND_USAGE_BACK = true;

    public static void initExecutor(UserDatabase userDatabase)
    {
        Bukkit.getPluginCommand("3pa").setExecutor(
            (sender, command, alias, args) ->
            {
                new ThirparauthCommandExecution(sender, command, alias, args, userDatabase);
                return DO_NOT_SEND_COMMAND_USAGE_BACK;
            }
        );
    }

    private final UserDatabase _userDatabase;

    private ThirparauthCommandExecution(CommandSender sender, Command command, String alias, String[] rawArgs, UserDatabase userDatabase)
    {
        super(sender, command, alias, rawArgs);
        _userDatabase = userDatabase;

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
        sender.sendMessage("§e----------- §fHelp: /"+alias+" §e-------------------");

        if (command.getName().equals(alias) == false)
        {
            sender.sendMessage("§6Alias for: §f/"+command.getName());
        }

        sender.sendMessage("§6Description: §r"+command.getDescription());
        sender.sendMessage("§6Usage: §r"+command.getUsage());
        sender.sendMessage("§6Aliases: §r"+String.join(", ", command.getAliases()));
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
                playerSetPassword();
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

    private void playerSetPassword()
    {
        new TextInputProcess((Player)sender)
        {
            @Override
            protected void onSubmitted(BookMeta book)
            {
                try
                {
                    String enteredPassword = Args.mergedPagesOf(book);
                    UserData senderData = _userDatabase.dataOf(player);
                    if (senderData == null)
                    {
                        _userDatabase.register(player, enteredPassword);
                        sender.sendMessage("§2You have successfully set up a 3PA");
                    }
                    else
                    {
                        senderData.setPassword(enteredPassword);
                        sender.sendMessage("§2You have successfully changed Your password");
                    }
                }
                catch (Exception exception)
                {
                    Console.error("An error occurred while "+sender.getName()+" submitted a password after /3pa set-password");
                    Console.error("thrown : "+exception);
                }
                stop();
            }
        };
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
