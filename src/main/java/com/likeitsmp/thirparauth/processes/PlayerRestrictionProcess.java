package com.likeitsmp.thirparauth.processes;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.ArrowBodyCountChangeEvent;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityEnterBlockEvent;
import org.bukkit.event.entity.EntityEnterLoveModeEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityKnockbackEvent;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PigZombieAngerEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerSignOpenEvent;
import org.bukkit.event.player.PlayerSpawnChangeEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.likeitsmp.events.PlayerAutoListener;

public class PlayerRestrictionProcess extends PlayerAutoListener
{
    public PlayerRestrictionProcess(Player player, Plugin plugin)
    {
        super(player, plugin);
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

    @EventHandler(priority = EventPriority.LOWEST)
    protected void handle(PlayerEditBookEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerInteractEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(SheepDyeWoolEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerMoveEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(AsyncPlayerChatEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerVelocityEvent event)
    {
        boolean isNotCancelled = event.isCancelled() == false;
        boolean theRightPlayer = event.getPlayer() == player;
        if (isNotCancelled && theRightPlayer)
        {
            event.setVelocity(new Vector(0d, 0d, 0d));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerAnimationEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerBedEnterEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerBedLeaveEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerBucketEntityEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerBucketEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerDropItemEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerFishEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerGameModeChangeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerHarvestBlockEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerInteractEntityEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerItemConsumeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerItemDamageEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerItemHeldEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerItemMendEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerRecipeDiscoverEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerShearEntityEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerSignOpenEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerSpawnChangeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerStatisticIncrementEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerSwapHandItemsEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerTakeLecternBookEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerToggleFlightEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerToggleSneakEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PlayerToggleSprintEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(AreaEffectCloudApplyEvent event)
    {
        boolean isNotCancelled = event.isCancelled() == false;
        if (isNotCancelled)
        {
            event.getAffectedEntities().remove(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(PortalCreateEvent event)
    {
        if (event.isCancelled() == false && event.getEntity() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityDamageEvent event)
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
    protected void handle(EntityDismountEvent event)
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
    protected void handle(PigZombieAngerEvent event)
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
    protected void handle(ProjectileHitEvent event)
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
    protected void handle(EntityMountEvent event)
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
    protected void handle(EntityPlaceEvent event)
    {
        if (event.isCancelled() == false && event.getPlayer() == player)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityTargetEvent event)
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
    protected void handle(EntityToggleGlideEvent event)
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
    protected void handle(EntityToggleSwimEvent event)
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
    protected void handle(EntityTameEvent event)
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
    protected void handle(ArrowBodyCountChangeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityAirChangeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityChangeBlockEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityCombustEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityDropItemEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityEnterBlockEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityEnterLoveModeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityExhaustionEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityExplodeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityInteractEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityKnockbackEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityPickupItemEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityPotionEffectEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityShootBowEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntitySpellCastEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(EntityTransformEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(ExplosionPrimeEvent event)
    {
        handleAbstract(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    protected void handle(FoodLevelChangeEvent event)
    {
        handleAbstract(event); 
    }
}
