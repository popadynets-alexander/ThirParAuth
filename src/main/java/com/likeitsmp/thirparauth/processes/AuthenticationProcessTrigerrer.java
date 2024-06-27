package com.likeitsmp.thirparauth.processes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.likeitsmp.thirparauth.data.UserData;
import com.likeitsmp.thirparauth.data.UserDatabase;

public final class AuthenticationProcessTrigerrer implements Listener
{
    private final Plugin _plugin;
    private final UserDatabase _userDatabase;

    public AuthenticationProcessTrigerrer(Plugin plugin, UserDatabase userDatabase)
    {
        _plugin = plugin;
        _userDatabase = userDatabase;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void handle(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        UserData playerData = _userDatabase.dataOf(player);
        if (playerData == null)
        {
            return;
        }

        new AuthenticationProcess(player, playerData, _plugin);
    }
}
