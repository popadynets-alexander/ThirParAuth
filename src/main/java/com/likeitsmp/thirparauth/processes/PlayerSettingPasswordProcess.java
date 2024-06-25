package com.likeitsmp.thirparauth.processes;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

import com.likeitsmp.commands.Args;
import com.likeitsmp.console.Console;
import com.likeitsmp.thirparauth.data.UserData;
import com.likeitsmp.thirparauth.data.UserDatabase;

public final class PlayerSettingPasswordProcess extends TextInputProcess
{
    private final UserDatabase _userDatabase;

    public PlayerSettingPasswordProcess(Player player, Plugin plugin, UserDatabase userDatabase)
    {
        super(player, plugin);
        _userDatabase = userDatabase;
    }

    @Override
    protected void setupInputField(BookMeta book)
    {
        book.setDisplayName("Your New Password");
        book.setLore(List.of(
            "write your new password in the book",
            "and then hit \"Done\" to submit it"
        ));
    }

    @Override
    protected void onSubmittedTextInput(PlayerEditBookEvent event)
    {
        try
        {
            BookMeta submittedBook = event.getNewBookMeta();
            String enteredPassword = Args.mergedPagesOf(submittedBook);
            UserData playerData = _userDatabase.dataOf(player);
            if (playerData == null)
            {
                _userDatabase.register(player, enteredPassword);
                player.sendMessage("§2You have successfully set up a 3PA");
            }
            else
            {
                playerData.setPassword(enteredPassword);
                player.sendMessage("§2You have successfully changed Your password");
            }
        }
        catch (Exception exception)
        {
            Console.error("An unhandled error occurred while "+player.getName()+" submitted a password after /3pa set-password");
            Console.error("thrown : "+exception);
            player.sendMessage("An unhandled error occurred : "+exception);
        }

        stop();
    }
}