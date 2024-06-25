package com.likeitsmp.thirparauth.processes;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

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

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerEditBookEvent event)
    {
        boolean isNotCancelled = event.isCancelled() == false;
        boolean theRightPlayer = event.getPlayer() == player;
        if (isNotCancelled && theRightPlayer)
        {
            onSubmittedTextInput(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerInteractEvent event)
    {
        if (event.getPlayer() != player ||
            event.useItemInHand() == Result.DENY)
        {
            return;
        }

        if (event.getAction() != Action.LEFT_CLICK_AIR ||
            event.getItem().getType() == Material.WRITABLE_BOOK)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PluginDisableEvent event)
    {
        if (event.getPlugin() == plugin)
        {
            stop();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerKickEvent event)
    {
        boolean isNotCancelled = event.isCancelled() == false;
        boolean theRightPlayer = event.getPlayer() == player;
        if (isNotCancelled && theRightPlayer)
        {
            stop();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerQuitEvent event)
    {
        boolean theRightPlayer = event.getPlayer() == player;
        if (theRightPlayer)
        {
            stop();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerMoveEvent event)
    {
        boolean isNotCancelled = event.isCancelled() == false;
        boolean theRightPlayer = event.getPlayer() == player;
        if (isNotCancelled && theRightPlayer)
        {
            Location a = event.getFrom();
            Location b = event.getTo();
            boolean deltaX = a.getX() != b.getX();
            boolean deltaY = a.getY() != b.getY();
            boolean deltaZ = a.getZ() != b.getZ();
            event.setCancelled(deltaX | deltaY | deltaZ);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(AsyncPlayerChatEvent event)
    {
        // TODO
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerVelocityEvent event)
    {
        boolean isNotCancelled = event.isCancelled() == false;
        boolean theRightPlayer = event.getPlayer() == player;
        if (isNotCancelled && theRightPlayer)
        {
            event.setVelocity(new Vector(0d, 0d, 0d));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerAnimationEvent event)
    {
        handleAbstract(event);
    }

    <CancellablePlayerEvent extends PlayerEvent & Cancellable>
    void handleAbstract(CancellablePlayerEvent event)
    {
        boolean isNotCancelled = event.isCancelled() == false;
        boolean theRightPlayer = event.getPlayer() == player;
        if (isNotCancelled && theRightPlayer)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerBedEnterEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerBedLeaveEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerBucketEntityEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerBucketEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerDropItemEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerFishEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerGameModeChangeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerHarvestBlockEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerInteractEntityEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerItemConsumeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerItemDamageEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerItemHeldEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerItemMendEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerRecipeDiscoverEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerShearEntityEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerSignOpenEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerSpawnChangeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerStatisticIncrementEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerSwapHandItemsEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerTakeLecternBookEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerToggleFlightEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerToggleSneakEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PlayerToggleSprintEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(AreaEffectCloudApplyEvent event)
    {
        boolean isNotCancelled = event.isCancelled() == false;
        if (isNotCancelled)
        {
            event.getAffectedEntities().remove(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PortalCreateEvent event)
    {
        if (event.isCancelled() == false && event.getEntity() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityDamageEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getDamageSource() == player ||
            event.getEntity() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityDismountEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getDismounted() == player ||
            event.getEntity() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(PigZombieAngerEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getTarget() == player)
        {
            event.setNewAnger(0);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(ProjectileHitEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getHitEntity() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(SheepDyeWoolEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getPlayer() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityMountEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getEntity() == event ||
            event.getMount() == event)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityPlaceEvent event)
    {
        if (event.isCancelled() == false && event.getPlayer() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityTargetEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getTarget() == player ||
            event.getEntity() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityToggleGlideEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getEntity() == player)
        {
            event.setCancelled(event.isGliding());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityToggleSwimEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getEntity() == player)
        {
            event.setCancelled(event.isSwimming());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityTameEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getOwner() == player ||
            event.getEntity() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(ArrowBodyCountChangeEvent event)
    {
        handleAbstract(event);
    }

    <CancellableEntityEvent extends EntityEvent & Cancellable>
    void handleAbstract(CancellableEntityEvent event)
    {
        boolean isNotCancelled = event.isCancelled() == false;
        boolean theRightEntity = event.getEntity() == player;
        if (isNotCancelled && theRightEntity)
        {
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityAirChangeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityChangeBlockEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityCombustEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityDropItemEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityEnterBlockEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityEnterLoveModeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityExhaustionEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityExplodeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityInteractEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityKnockbackEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityPickupItemEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityPotionEffectEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityShootBowEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntitySpellCastEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(EntityTransformEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(ExplosionPrimeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void handle(FoodLevelChangeEvent event)
    {
        handleAbstract(event); 
    }
}
