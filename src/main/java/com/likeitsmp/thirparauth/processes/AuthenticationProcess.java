package com.likeitsmp.thirparauth.processes;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

import com.likeitsmp.commands.Args;
import com.likeitsmp.console.Console;
import com.likeitsmp.thirparauth.data.UserData;

public class AuthenticationProcess extends TextInputProcess
{
    private final UserData _playerData;

    public AuthenticationProcess(Player player, UserData playerData, Plugin plugin)
    {
        super(player, plugin);
        _playerData = playerData;
        Console.warning("BEGAN AUTHENTICATION PROCESS");
    }

    @Override
    protected void onSubmittedTextInput(PlayerEditBookEvent event)
    {
        BookMeta book = event.getNewBookMeta();
        String enteredPassword = Args.mergedPagesOf(book);
        if (_playerData.verifies(enteredPassword))
        {
            player.sendMessage("§2Welcome!");
            stop();
        }
        else
        {
            player.sendMessage("§4Wrong password");
        }
    }
}
