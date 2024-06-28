package com.likeitsmp.console;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import com.google.common.base.Supplier;

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

    public static void debugRun(Runnable... runnables)
    {
        for (Runnable runnable : runnables)
        {
            try
            {
                runnable.run();
            }
            catch (Exception e)
            {
                error(e);
            }
        }
    }

    public static <T> Consumer<T> debug(Consumer<? super T> consumer)
    {
        return debugConsumer(consumer);
    }

    public static <T> Consumer<T> debugConsumer(Consumer<? super T> consumer)
    {
        return (value) ->
        {
            try
            {
                consumer.accept(value);
            }
            catch (Exception e)
            {
                error(e);
            }
        };
    }

    public static <A, B> BiConsumer<A, B> debug(BiConsumer<? super A, ? super B> biConsumer)
    {
        return debugBiConsumer(biConsumer);
    }

    public static <A, B> BiConsumer<A, B> debugBiConsumer(BiConsumer<? super A, ? super B> biConsumer)
    {
        return (a, b) ->
        {
            try
            {
                biConsumer.accept(a, b);
            }
            catch (Exception e)
            {
                error(e);
            }
        };
    }

    public static <I, O> Function<I, O> debug(Function<I, O> function)
    {
        return debugFunction(function);
    }
    
    public static <I, O> Function<I, O> debugFunction(Function<I, O> function)
    {
        return debugFunction(function, () -> null);
    }

    public static <I, O> Function<I, O> debugFunction(Function<I, O> function, Supplier<O> defaultValueSupplier)
    {
        return i ->
        {
            try
            {
                return function.apply(i);
            }
            catch (Exception e)
            {
                Console.error(e);
                return defaultValueSupplier.get();
            }
        };
    }

    public static <I, O> Function<I, O> debug(Function<I, O> function, Supplier<O> defaultValueSupplier)
    {
        return debugFunction(function, defaultValueSupplier);
    }
}
