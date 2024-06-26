package com.likeitsmp.thirparauth.processes;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

import com.likeitsmp.console.Console;
import com.likeitsmp.events.PlayerAutoListener;
import com.likeitsmp.thirparauth.data.PlayerDataSubstitution;

public abstract class TextInputProcess extends PlayerAutoListener
{
    private PlayerDataSubstitution _playerDataSubstitution;
    private PlayerRestrictionProcess _playerRestriction;

    public TextInputProcess(Player player, Plugin plugin)
    {
        super(player, plugin);
        _playerDataSubstitution = new TextInputDataSubstitution(player, plugin);
        _playerRestriction = new PlayerTextInputBindAndChatRestrictions(player, plugin);
    }

    protected abstract void onSubmittedTextInput(PlayerEditBookEvent event);

    @Override
    protected final void onStop()
    {
        _playerRestriction.stop();
        _playerDataSubstitution.revert();
    }

    protected Location lobbyLocation()
    {
        return new Location(
            Bukkit.getWorlds().get(0),
            0d, 150d, 0d
        );
    }

    protected void setupInputField(BookMeta book)
    {
        book.setDisplayName("Input Your Text Here");
        book.setLore(List.of(
            "And then hit \"Done\" to submit your text"
        ));
    }
    
    private final class TextInputDataSubstitution extends PlayerDataSubstitution
    {
        public TextInputDataSubstitution(Player player, Plugin plugin)
        {
            super(player, plugin);
        }

        @Override
        protected void restorePlayerData()
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, super::restorePlayerData);
            Console.warning("DELAYED RESTORATION OF PLAYER DATA");
        }

        @Override
        protected void substituteInventory()
        {
            super.substituteInventory();
            
            ItemStack inputField = new ItemStack(Material.WRITABLE_BOOK);

            BookMeta book = (BookMeta)inputField.getItemMeta();
            setupInputField(book);
            inputField.setItemMeta(book);

            player.getInventory().addItem(inputField);
        }

        @Override
        protected void substituteLocation()
        {
            player.teleport(lobbyLocation());
        }

        @Override
        protected void substituteHasGravity()
        {
            player.setGravity(false);
        }

        @Override
        protected void substituteAllowFlight()
        {
            final boolean DO_NOT_KICK_PLAYER_FOR_LEVITATING = true;
            player.setAllowFlight(DO_NOT_KICK_PLAYER_FOR_LEVITATING);
        }

        @Override
        protected void substituteIsInvulnerable()
        {
            player.setInvulnerable(true);
        }

        @Override
        protected void substituteIsInvisible()
        {
            player.setInvisible(true);
        }
    }

    private final class PlayerTextInputBindAndChatRestrictions extends PlayerRestrictionProcess
    {
        private final List<AsyncPlayerChatEvent> _chatRecording;

        public PlayerTextInputBindAndChatRestrictions(Player player, Plugin plugin)
        {
            super(player, plugin);
            _chatRecording = new LinkedList<>();
            Console.warning("STARTED INPUT BIND AND CHAT RESTRICTIONS");
        }

        @Override
        @EventHandler(priority = EventPriority.HIGHEST)
        protected void handle(AsyncPlayerChatEvent event)
        {
            if (event.isCancelled())
            {
                return;
            }

            if (event.getPlayer() == player)
            {
                event.setCancelled(true);
                player.sendMessage("§4You may not send messages now");
            }
            else if (event.getRecipients().remove(player))
            {
                _chatRecording.add(event);
            }
        }

        @Override
        @EventHandler(priority = EventPriority.HIGHEST)
        protected void handle(PlayerInteractEvent event)
        {
            if (event.getPlayer() != player ||
                event.useItemInHand() == Result.DENY)
            {
                return;
            }

            if (event.getAction() != Action.LEFT_CLICK_AIR ||
                event.getItem() == null ||
                event.getItem().getType() == Material.WRITABLE_BOOK)
            {
                event.setCancelled(true);
            }
        }

        @Override
        @EventHandler(priority = EventPriority.HIGHEST)
        protected void handle(PlayerEditBookEvent event)
        {
            if (event.isCancelled() == false &&
                event.getPlayer() == player)
            {
                onSubmittedTextInput(event);
            }
        }

        @Override
        protected void onStop()
        {
            _chatRecording.forEach(this::reenactMessage);
            _chatRecording.clear();
            Console.warning("FINISHED INPUT BIND AND CHAT RESTRICTIONS");
        }

        private void reenactMessage(AsyncPlayerChatEvent message)
        {
            if (message.isCancelled())
            {
                return;
            }

            String template = message.getFormat();
            String sender = message.getPlayer().getDisplayName();
            String contents = message.getMessage();
            String wholeMessage = template.formatted(sender, contents);
            player.sendMessage(wholeMessage);
        }
    }
}