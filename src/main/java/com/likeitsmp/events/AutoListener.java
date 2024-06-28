package com.likeitsmp.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public abstract class AutoListener implements Listener
{
    protected final Plugin plugin;
    private boolean _isActive;

    public AutoListener(Plugin plugin)
    {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        _isActive = true;
    }

    public final boolean isActive()
    {
        return _isActive;
    }

    public final void stop()
    {
        if (_isActive)
        {
            _isActive = false;
            HandlerList.unregisterAll(this);
            onStop();
        }
    }

    protected void onStop() { }

    @EventHandler(priority = EventPriority.MONITOR)
    private final void handle(PluginDisableEvent event)
    {
        if (event.getPlugin() == plugin)
        {
            stop();
        }
    } 
}
