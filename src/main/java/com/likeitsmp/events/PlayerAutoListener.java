package com.likeitsmp.events;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public abstract class PlayerAutoListener extends AutoListener
{
    protected final Player player;

    public PlayerAutoListener(Player player, Plugin plugin)
    {
        super(plugin);
        Objects.requireNonNull(player);
        this.player = player;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    private void handle(PlayerQuitEvent event)
    {
        if (event.getPlayer() == player)
        {
            stop();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void handle(PlayerKickEvent event)
    {
        if (event.isCancelled() == false &&
            event.getPlayer() == player)
        {
            stop();
        }
    }
}
