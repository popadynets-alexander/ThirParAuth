package com.likeitsmp.thirparauth.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import com.likeitsmp.console.Console;

public final class UserDatabase
{
    private final Map<UUID, UserData> _userDataMap;

    private final Plugin _plugin;

    public UserDatabase(Plugin plugin)
    {
        _plugin = plugin;
        _userDataMap = load();

        setupPeriodicAutoSaving();
        setupShutdownAutoSaving();
    }

    @SuppressWarnings("unchecked")
    private Map<UUID, UserData> load()
    {
        File databaseFile = databaseFile();
        try (
            FileInputStream fileInputStream = new FileInputStream(databaseFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ) {
            return (Map<UUID, UserData>) objectInputStream.readObject();
        }
        catch (Exception exception)
        {
            Console.error("Failed to load UserDatabase");
            Console.error("from : "+databaseFile);
            Console.error("thrown : "+exception);
            return new HashMap<>();
        }
    }

    private File databaseFile()
    {
        return new File(_plugin.getDataFolder(), "3pausrdb.jon");
    }

    private void setupPeriodicAutoSaving()
    {
        final int TICKS_PER_SECOND = 20;
        final int FIVE_MINUTES = 5 * 60 * TICKS_PER_SECOND;
        final int INITIAL_DELAY = FIVE_MINUTES;
        final int REPETITION_PERIOD = FIVE_MINUTES;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(_plugin, this::save, INITIAL_DELAY, REPETITION_PERIOD);
    }
    
    private void save()
    {
        File databaseFile = databaseFile();
        try (
            FileOutputStream fileOutputStream = new FileOutputStream(databaseFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ) {
            objectOutputStream.writeObject(_userDataMap);
        }
        catch (Exception exception)
        {
            Console.error("Failed to save UserDatabase");
            Console.error("to : "+databaseFile);
            Console.error("thrown : "+exception);
        }
    }

    private void setupShutdownAutoSaving()
    {
        Listener shutdownListener = new Listener()
        {
            @EventHandler
            private void handle(PluginDisableEvent event)
            {
                if (event.getPlugin() == _plugin)
                {
                    save();
                }
            }
        };
        Bukkit.getPluginManager().registerEvents(shutdownListener, _plugin);
    }

    public UserData dataOf(Player player)
    {
        UUID key = player.getUniqueId();
        return _userDataMap.get(key);
    }

    public void register(Player player, String password)
    {
        if (dataOf(player) != null)
        {
            throw new RuntimeException("Tried to register "+player.getName()+" twice");
        }

        UUID key = player.getUniqueId();
        UserData userData = new UserData(password);
        _userDataMap.put(key, userData);
    }
}
