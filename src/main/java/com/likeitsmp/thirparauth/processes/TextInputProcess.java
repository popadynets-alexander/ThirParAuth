package com.likeitsmp.thirparauth.processes;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

public abstract class TextInputProcess implements Listener
{
    protected final Player player;
    protected final Plugin plugin;
    private boolean _isActive;
    private final PlayerDataStash _playerDataStash;

    public TextInputProcess(Player player, Plugin plugin)
    {
        this.player = player;
        this.plugin = plugin;

        _playerDataStash = new PlayerDataStash(player);
        putPlayerAwayFromWorld();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private void putPlayerAwayFromWorld()
    {
        PlayerDataStash.annul(player);
        
        player.teleport(lobbyLocation());

        initializeInputField();
    }

    protected Location lobbyLocation()
    {
        return new Location(
            Bukkit.getWorlds().get(0),
            0d, 150d, 0d
        );
    }

    private void initializeInputField()
    {
        ItemStack inputField = new ItemStack(Material.WRITABLE_BOOK);
        
        BookMeta book = (BookMeta)inputField.getItemMeta();
        setupInputField(book);
        inputField.setItemMeta(book);

        player.getInventory().addItem(inputField);
    }

    protected void setupInputField(BookMeta book)
    {
        book.setDisplayName("Input Your Text Here");
        book.setLore(List.of(
            "And then hit \"Done\" to submit your text"
        ));
    }

    public final boolean isActive()
    {
        return _isActive;
    }

    protected abstract void onSubmittedTextInput(PlayerEditBookEvent event);

    public final void stop()
    {
        if (_isActive)
        {
            _isActive = false;
            HandlerList.unregisterAll(this);
            _playerDataStash.reapply();
        }
    }

    @EventHandler
    private void handle(PlayerEditBookEvent event)
    {
        if (event.getPlayer() == player)
        {
            onSubmittedTextInput(event);
        }
    }

    @EventHandler
    private void handle(PluginDisableEvent event)
    {
        if (event.getPlugin() == plugin)
        {
            stop();
        }
    }
}
