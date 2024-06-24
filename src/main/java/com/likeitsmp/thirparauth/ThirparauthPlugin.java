package com.likeitsmp.thirparauth;

import org.bukkit.plugin.java.JavaPlugin;

import com.likeitsmp.thirparauth.command.ThirparauthCommandExecution;
import com.likeitsmp.thirparauth.data.UserDatabase;

public class ThirparauthPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        UserDatabase userDatabase = new UserDatabase(this);
        ThirparauthCommandExecution.initExecutor(this, userDatabase);
    }
}
