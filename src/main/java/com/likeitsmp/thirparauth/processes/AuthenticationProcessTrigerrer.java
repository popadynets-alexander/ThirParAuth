package com.likeitsmp.thirparauth.processes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.likeitsmp.events.AutoListener;
import com.likeitsmp.thirparauth.data.UserData;
import com.likeitsmp.thirparauth.data.UserDatabase;

public final class AuthenticationProcessTrigerrer extends AutoListener
{
    private final UserDatabase _userDatabase;

    public AuthenticationProcessTrigerrer(Plugin plugin, UserDatabase userDatabase)
    {
        super(plugin);
        _userDatabase = userDatabase;
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

        new AuthenticationProcess(player, playerData, plugin);
    }
}
