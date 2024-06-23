package com.likeitsmp.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandExecution
{
    protected final String alias;
    protected final Command command;
    protected final CommandSender sender;
    protected final List<String> args;

    protected CommandExecution(CommandSender sender, Command command, String alias, String[] rawArgs)
    {
        this.alias = alias;
        this.sender = sender;
        this.command = command;
        this.args = List.of(Args.resplit(rawArgs));
    }

    protected final int argsCount()
    {
        return args.size();
    }

    protected final String arg(int index)
    {
        return args.get(index);
    }
}
