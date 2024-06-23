package com.likeitsmp.thirparauth;

import org.bukkit.plugin.java.JavaPlugin;

import com.likeitsmp.thirparauth.command.ThirparauthCommandExecution;

public class ThirparauthPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        ThirparauthCommandExecution.initExecutor();
    }
}
