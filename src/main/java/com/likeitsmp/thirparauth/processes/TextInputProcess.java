package com.likeitsmp.thirparauth.processes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

public abstract class TextInputProcess
{
    protected final Player player;

    public TextInputProcess(Player player)
    {
        this.player = player;
        throw new UnsupportedOperationException();
    }

    protected abstract void onSubmitted(BookMeta book);

    public void stop()
    {
        throw new UnsupportedOperationException();
    }
}
