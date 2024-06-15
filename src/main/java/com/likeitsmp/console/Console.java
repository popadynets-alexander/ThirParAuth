package com.likeitsmp.console;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

public final class Console
{
    private Console() { }

    private final static Logger logger = Bukkit.getLogger();

    public static void log(Object message)
    {
        for (String line : linesOf(message))
        {
            logger.info(line);
        }
    }

    public static void warning(Object message)
    {
        for (String line : linesOf(message))
        {
            logger.warning(line);
        }
    }

    public static void error(Object message)
    {
        for (String line : linesOf(message))
        {
            logger.severe(line);
        }
    }

    private static String[] linesOf(Object message)
    {
        String messageString = String.valueOf(message);
        return messageString.split("\n");
    }
}
