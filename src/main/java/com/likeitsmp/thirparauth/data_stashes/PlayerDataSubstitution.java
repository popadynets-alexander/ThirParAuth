package com.likeitsmp.thirparauth.data_stashes;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import com.likeitsmp.console.Console;

public class PlayerDataSubstitution
{
    protected final int DEFAULT_FOOD_LEVEL = 20;
    protected final int DEFAULT_SATURATION = 20;
    protected final int TICKS_PER_SECOND = 20; // it's just a coincidence
    protected final int DEFAULT_MAXIMUM_AIR = 15 * TICKS_PER_SECOND;
    protected final int DEFAULT_MAXIMUM_NO_DAMAGE_TICKS = 1 * TICKS_PER_SECOND;
    protected final int DEFAULT_PORTAL_COOLDOWN = 15 * TICKS_PER_SECOND;
    protected final int DEFAULT_SATURATED_REGEN_RATE = 1 * TICKS_PER_SECOND;
    protected final int DEFAULT_STARVATION_RATE = 2 * TICKS_PER_SECOND;
    protected final int DEFAULT_UNSATURATED_REGEN_RATE = 4 * TICKS_PER_SECOND;
    protected static final float DEFAULT_WALK_SPEED = 0.15f;
    protected static final double DEFAULT_HEALTH = 20;

    protected final Player player;
    protected final Plugin plugin;
    private boolean _isActive;
    private boolean _isOp;
    private boolean _isWhitelisted;
    private boolean _canPickupItems;
    private boolean _isCollidable;
    private List<PermissionAttachmentInfo> _permissionAttachments;
    private GameMode _gameMode;
    private Scoreboard _scoreboard;
    private Map<Statistic, Integer> _statistics;
    private List<PotionEffect> _potionEffects;
    private List<AttributeSubstitution> _attributes;
    private Map<Material, Integer> _itemUsageCooldowns;
    private ItemStack _itemOnCursor;
    private int _itemInUseTicks;
    private ItemStack[] _inventoryContents;
    private ItemStack[] _enderChestContents;
    private Location _location;
    private Location _lastDeathLocation;
    private Location _respawnLocation;
    private Vector _velocity;
    private Entity _spectatorTarget;
    private Entity _vehicle;
    private List<Entity> _passengers;
    private String _customName;
    private String _displayName;
    private String _playerListName;
    private String _playerListHeader;
    private String _playerListFooter;
    private boolean _allowFlight;
    private float _flySpeed;
    private boolean _isFlying;
    private boolean _isGliding;
    private boolean _isSneaking;
    private boolean _isSprinting;
    private boolean _isSwimming;
    private boolean _hasGravity;
    private boolean _isGlowing;
    private boolean _isInvisible;
    private boolean _isInvulnerable;
    private boolean _isSilent;
    private boolean _sleepingIsIgnored;
    private boolean _isVisibleByDefault;
    private int _arrowsInBody;
    private int _arrowCooldown;
    private int _enchantmentSeed;
    private int _foodLevel;
    private int _fireTicks;
    private boolean _hasVisualFire;
    private int _freezeTicks;
    private int _expLevel;
    private float _exp;
    private int _expCooldown;
    private int _totalExperience;
    private int _remainingAir;
    private int _maximumAir;
    private int _noActionTicks;
    private int _noDamageTicks;
    private int _maximumNoDamageTicks;
    private int _portalCooldown;
    private int _saturatedRegenRate;
    private int _starvationRate;
    private int _unsaturatedRegenRate;
    private int _ticksLived;
    private float _saturation;
    private float _exhaustion;
    private float _fallDistance;
    private float _walkSpeed;
    private double _absorptionAmount;
    private double _health;
    private double _healthScale;
    private boolean _healthIsScaled;
    private double _lastDamage;

    public PlayerDataSubstitution(Player player, Plugin plugin)
    {
        this.player = player;
        this.plugin = plugin;

        storePlayerData();
        substituteLoadedPlayerData();
        _isActive = true;
        afterInit();
    }

    private void storePlayerData()
    {
        Console.debugRun(
            () -> Console.warning("BEGAN SAVING PLAYER'S DATA"),
            () -> _isOp = player.isOp(),
            () -> _isWhitelisted = player.isWhitelisted(),
            () -> _canPickupItems = player.getCanPickupItems(),
            () -> _isCollidable = player.isCollidable(),
            () -> _permissionAttachments = List.copyOf(player.getEffectivePermissions()),

            () -> _gameMode = player.getGameMode(),

            () -> _scoreboard = player.getScoreboard(),
            () -> _statistics = Stream.of(Statistic.values())
                                      .collect(Collectors.toMap(
                                          statistic -> statistic,
                                          Console.debug(player::getStatistic, () -> 0)
                                      )),
            () -> _potionEffects = List.copyOf(player.getActivePotionEffects()),
            () -> _attributes = Stream.of(Attribute.values())
                                      .map(Console.debug(player::getAttribute))
                                      .filter(attributeInstance -> attributeInstance != null)
                                      .map(Console.debug(AttributeSubstitution::new))
                                      .filter(attributeStash -> attributeStash != null)
                                      .toList(),

            () -> _itemUsageCooldowns = Stream.of(Material.values())
                                             .filter(Material::isItem)
                                             .collect(Collectors.toMap(
                                                 itemType -> itemType,
                                                 Console.debug(player::getCooldown, () -> 0)
                                             )),
            () -> _itemOnCursor = player.getItemOnCursor(),
            () -> _itemInUseTicks = player.getItemInUseTicks(),
            () -> _inventoryContents = player.getInventory().getContents(),
            () -> _enderChestContents = player.getEnderChest().getContents(),

            () -> _location = cloneOf(player.getLocation()),
            () -> _lastDeathLocation = cloneOf(player.getLastDeathLocation()),
            () -> _respawnLocation = cloneOf(player.getRespawnLocation()),
            () -> _velocity = player.getVelocity().clone(),

            () -> _spectatorTarget = player.getSpectatorTarget(),
            () -> _vehicle = player.getVehicle(),
            () -> _passengers = List.copyOf(player.getPassengers()),
            
            () -> _customName = player.getCustomName(),
            () -> _displayName = player.getDisplayName(),
            () -> _playerListName = player.getPlayerListName(),
            () -> _playerListHeader = player.getPlayerListHeader(),
            () -> _playerListFooter = player.getPlayerListFooter(),

            () -> _allowFlight = player.getAllowFlight(),
            () -> _flySpeed = player.getFlySpeed(),
            () -> _isFlying = player.isFlying(),
            () -> _isGliding = player.isGliding(),
            () -> _isSneaking = player.isSneaking(),
            () -> _isSprinting = player.isSprinting(),
            () -> _isSwimming = player.isSwimming(),
            () -> _hasGravity = player.hasGravity(),

            () -> _isGlowing = player.isGlowing(),
            () -> _isInvisible = player.isInvisible(),
            () -> _isInvulnerable = player.isInvulnerable(),
            () -> _isSilent = player.isSilent(),
            () -> _sleepingIsIgnored = player.isSleepingIgnored(),
            () -> _isVisibleByDefault = player.isVisibleByDefault(),
            
            () -> _arrowsInBody = player.getArrowsInBody(),
            () -> _arrowCooldown = player.getArrowCooldown(),
            () -> _enchantmentSeed = player.getEnchantmentSeed(),
            () -> _foodLevel = player.getFoodLevel(),

            () -> _fireTicks = player.getFireTicks(),
            () -> _hasVisualFire = player.isVisualFire(),
            () -> _freezeTicks = player.getFreezeTicks(),
            
            () -> _expLevel = player.getLevel(),
            () -> _exp = player.getExp(),
            () -> _expCooldown = player.getExpCooldown(),
            () -> _totalExperience = player.getTotalExperience(),
            
            () -> _remainingAir = player.getRemainingAir(),
            () -> _maximumAir = player.getMaximumAir(),

            () -> _noActionTicks = player.getNoActionTicks(),
            () -> _noDamageTicks = player.getNoDamageTicks(),
            () -> _maximumNoDamageTicks = player.getMaximumNoDamageTicks(),
            () -> _portalCooldown = player.getPortalCooldown(),
            () -> _saturatedRegenRate = player.getSaturatedRegenRate(),
            () -> _starvationRate = player.getStarvationRate(),
            () -> _unsaturatedRegenRate = player.getUnsaturatedRegenRate(),
            () -> _ticksLived = player.getTicksLived(),

            () -> _saturation = player.getSaturation(),
            () -> _exhaustion = player.getExhaustion(),
            () -> _fallDistance = player.getFallDistance(),
            () -> _walkSpeed = player.getWalkSpeed(),
            () -> _absorptionAmount = player.getAbsorptionAmount(),
            () -> _health = player.getHealth(),
            () -> _healthScale = player.getHealthScale(),
            () -> _healthIsScaled = player.isHealthScaled(),
            () -> _lastDamage = player.getLastDamage(),
            () -> Console.warning("FINISHED SAVING PLAYER'S DATA")
        );
    }

    private static Location cloneOf(Location location)
    {
        if (location == null)
        {
            return null;
        }
        else
        {
            return location.clone();
        }
    }

    //#region substitution
    protected void substituteLoadedPlayerData()
    {
        Console.debugRun(
            () -> Console.warning("BEGAN SUBSTITUTING PLAYER'S DATA"),
            this::substituteIsOp,
            this::substituteIsWhitelisted,
            this::substituteCanPickupItems,
            this::substituteIsCollidable,
            this::substitutePermissionAttachments,

            this::substituteGameMode,

            this::substituteScoreboard,
            this::substituteStatistics,
            this::substitutePotionEffects,
            this::substituteAttributes,

            this::substituteItemUsageCooldowns,
            this::substituteItemOnCursor,
            this::substituteItemInUseTicks,
            this::substituteInventory,
            this::substituteEnderChest,

            this::substituteLocation,
            this::substituteLastDeathLocation,
            this::substituteRespawnLocation,
            this::substituteVelocity,

            this::substituteSpectatorTarget,
            this::substituteVehicle,
            this::substitutePassengers,
            
            this::substituteCustomName,
            this::substituteDisplayName,
            this::substitutePlayerListName,
            this::substitutePlayerListHeader,
            this::substitutePlayerListFooter,

            this::substituteAllowFlight,
            this::substituteFlySpeed,
            this::substituteIsFlying,
            this::substituteIsGliding,
            this::substituteIsSneaking,
            this::substituteIsSprinting,
            this::substituteIsSwimming,
            this::substituteHasGravity,

            this::substituteIsGlowing,
            this::substituteIsInvisible,
            this::substituteIsInvulnerable,
            this::substituteIsSilent,
            this::substituteSleepingIsIgnored,
            this::substituteIsVisibleByDefault,
            
            this::substituteArrowsInBody,
            this::substituteArrowCooldown,
            this::substituteEnchantmentSeed,
            this::substituteFoodLevel,

            this::substituteFireTicks,
            this::substituteHasVisualFire,
            this::substituteFreezeTicks,
            
            this::substituteExpLevel,
            this::substituteExp,
            this::substituteExpCooldown,
            this::substituteTotalExperience,
            
            this::substituteRemainingAir,
            this::substituteMaximumAir,

            this::substituteNoActionTicks,
            this::substituteNoDamageTicks,
            this::substituteMaximumNoDamageTicks,
            this::substitutePortalCooldown,
            this::substituteSaturatedRegenRate,
            this::substituteStarvationRate,
            this::substituteUnsaturatedRegenRate,
            this::substituteTicksLived,

            this::substituteSaturation,
            this::substituteExhaustion,
            this::substituteFallDistance,
            this::substituteWalkSpeed,
            this::substituteAbsorptionAmount,
            this::substituteHealth,
            this::substituteHealthScale,
            this::substituteHealthIsScaled,
            this::substituteLastDamage,
            () -> Console.warning("FINISHED SUBSTITUTING PLAYER'S DATA")
        );
    }

    protected void substituteIsOp()
    {
        player.setOp(false);
    }
    
    protected void substituteIsWhitelisted()
    {
        player.setWhitelisted(Bukkit.isWhitelistEnforced());
    }
    
    protected void substituteCanPickupItems()
    {
        player.setCanPickupItems(true);
    }

    protected void substituteIsCollidable()
    {
        player.setCollidable(true);
    }
    
    protected void substituteAllowFlight()
    {
        boolean defaultAllowFlight = Bukkit.getServer().getAllowFlight();
        player.setAllowFlight(defaultAllowFlight);
    }

    protected void substituteFlySpeed()
    {
        player.setFlySpeed(0);
    }

    protected void substituteIsFlying()
    {
        player.setFlying(false);
    }

    protected void substituteIsGlowing()
    {
        player.setGlowing(false);
    }

    protected void substituteIsGliding()
    {
        player.setGliding(false);
    }

    protected void substituteHealthIsScaled()
    {
        player.setHealthScaled(false);
    }

    protected void substituteHasGravity()
    {
        player.setGravity(true);
    }

    protected void substituteIsInvisible()
    {
        player.setInvisible(false);
    }

    protected void substituteIsInvulnerable()
    {
        player.setInvulnerable(false);
    }

    protected void substituteIsSilent()
    {
        player.setSilent(false);
    }

    protected void substituteSleepingIsIgnored()
    {
        player.setSleepingIgnored(false);
    }

    protected void substituteIsSneaking()
    {
        player.setSneaking(false);
    }

    protected void substituteIsSprinting()
    {
        player.setSprinting(false);
    }

    protected void substituteIsSwimming()
    {
        player.setSwimming(false);
    }

    protected void substituteIsVisibleByDefault()
    {
        player.setVisibleByDefault(true);
    }

    protected void substituteHasVisualFire()
    {
        player.setVisualFire(false);
    }

    protected void substituteEnchantmentSeed()
    {
        player.setEnchantmentSeed(new Random().nextInt());
    }

    protected void substituteArrowCooldown()
    {
        player.setArrowCooldown(0);
    }

    protected void substituteArrowsInBody()
    {
        player.setArrowsInBody(0);
    }

    protected void substituteExpCooldown()
    {
        player.setExpCooldown(0);
    }

    protected void substituteFoodLevel()
    {
        player.setFoodLevel(DEFAULT_FOOD_LEVEL);
    }

    protected void substituteFireTicks()
    {
        player.setFireTicks(0);
    }

    protected void substituteFreezeTicks()
    {
        player.setFreezeTicks(0);
    }

    protected void substituteItemInUseTicks()
    {
        player.setItemInUseTicks(0);
    }

    protected void substituteExpLevel()
    {
        player.setLevel(0);
    }

    protected void substituteMaximumAir()
    {
        player.setMaximumAir(DEFAULT_MAXIMUM_AIR);
    }

    protected void substituteMaximumNoDamageTicks()
    {
        player.setMaximumNoDamageTicks(DEFAULT_MAXIMUM_NO_DAMAGE_TICKS);
    }

    protected void substituteNoActionTicks()
    {
        player.setNoActionTicks(0);
    }

    protected void substituteNoDamageTicks()
    {
        player.setNoDamageTicks(0);
    }

    protected void substitutePortalCooldown()
    {
        player.setPortalCooldown(DEFAULT_PORTAL_COOLDOWN);
    }

    protected void substituteRemainingAir()
    {
        player.setRemainingAir(0);
    }

    protected void substituteSaturatedRegenRate()
    {
        player.setSaturatedRegenRate(DEFAULT_SATURATED_REGEN_RATE);
    }

    protected void substituteStarvationRate()
    {
        player.setStarvationRate(DEFAULT_STARVATION_RATE);
    }

    protected void substituteUnsaturatedRegenRate()
    {
        player.setUnsaturatedRegenRate(DEFAULT_UNSATURATED_REGEN_RATE);
    }

    protected void substituteTicksLived()
    {
        player.setTicksLived(1);
    }

    protected void substituteTotalExperience()
    {
        player.setTotalExperience(0);
    }

    protected void substituteSaturation()
    {
        player.setSaturation(DEFAULT_SATURATION);
    }

    protected void substituteExp()
    {
        player.setExp(0);
    }

    protected void substituteExhaustion()
    {
        player.setExhaustion(0);
    }

    protected void substituteFallDistance()
    {
        player.setFallDistance(0);
    }

    protected void substituteWalkSpeed()
    {
        player.setWalkSpeed(DEFAULT_WALK_SPEED);
    }

    protected void substituteAbsorptionAmount()
    {
        player.setAbsorptionAmount(0);
    }

    protected void substituteHealth()
    {
        player.setHealth(DEFAULT_HEALTH);
    }

    protected void substituteHealthScale()
    {
        player.setHealthScale(DEFAULT_HEALTH);
    }

    protected void substituteLastDamage()
    {
        player.setLastDamage(0);
    }

    protected void substituteGameMode()
    {
        GameMode defaultGameMode = Bukkit.getDefaultGameMode();
        player.setGameMode(defaultGameMode);
    }

    protected void substituteCustomName()
    {
        player.setCustomName(null);
    }

    protected void substituteDisplayName()
    {
        player.setDisplayName(null);
    }

    protected void substitutePlayerListName()
    {
        player.setPlayerListName(null);
    }

    protected void substitutePlayerListHeader()
    {
        player.setPlayerListHeader(null);
    }

    protected void substitutePlayerListFooter()
    {
        player.setPlayerListFooter(null);
    }

    protected void substituteLocation()
    {
        World world = Bukkit.getWorlds().get(0);
        player.teleport(world.getSpawnLocation());
    }

    protected void substituteLastDeathLocation()
    {
        player.setLastDeathLocation(null);
    }

    protected void substituteRespawnLocation()
    {
        player.setRespawnLocation(null);
    }

    protected void substituteVelocity()
    {
        Vector zero = new Vector(0f, 0f, 0f);
        player.setVelocity(zero);
    }

    protected void substituteItemOnCursor()
    {
        player.setItemOnCursor(null);
    }

    protected void substituteInventory()
    {
        player.getInventory().clear();
    }

    protected void substituteEnderChest()
    {
        player.getEnderChest().clear();
    }

    protected void substituteSpectatorTarget()
    {
        player.setSpectatorTarget(null);
    }

    protected void substituteVehicle()
    {
        if (player.isInsideVehicle())
        {
            player.leaveVehicle();
        }
    }

    protected void substitutePassengers()
    {
        List.copyOf(player.getPassengers())
        .forEach(player::removePassenger);
    }

    protected void substituteScoreboard()
    {
        Scoreboard defaultScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        player.setScoreboard(defaultScoreboard);
    }

    protected void substituteItemUsageCooldowns()
    {
        Stream.of(Material.values())
        .filter(Material::isItem)
        .forEach(Console.debugConsumer(this::substituteCooldownForItemType));
    }

    protected void substituteCooldownForItemType(Material item)
    {
        player.setCooldown(item, 0);
    }

    protected void substituteStatistics()
    {
        Stream.of(Statistic.values())
        .forEach(Console.debugConsumer(
            stat -> player.setStatistic(stat, 0)
        ));
    }

    protected void substitutePermissionAttachments()
    {
        clearPlayerPermissions();
    }

    private void clearPlayerPermissions()
    {
        List.copyOf(player.getEffectivePermissions())
        .stream()
        .map(PermissionAttachmentInfo::getAttachment)
        .filter(attachment -> attachment != null)
        .forEach(Console.debug(player::removeAttachment));
    }

    protected void substitutePotionEffects()
    {
        clearPotionEffects();
    }

    protected final void clearPotionEffects()
    {
        List.copyOf(player.getActivePotionEffects())
        .stream()
        .map(PotionEffect::getType)
        .forEach(Console.debug(player::removePotionEffect));
    }

    protected void substituteAttributes()
    {
        Stream.of(Attribute.values())
        .map(Console.debug(player::getAttribute))
        .filter(attribute -> attribute != null)
        .forEach(this::substituteAttribute);
    }

    protected void substituteAttribute(AttributeInstance attribute)
    {
        clearModifiersOf(attribute);
    }

    protected final void clearModifiersOf(AttributeInstance attribute)
    {
        attribute.getModifiers()
        .forEach(Console.debug(attribute::removeModifier));
    }
    //#endregion

    protected void afterInit() { }

    public final void stop()
    {
        if (_isActive)
        {
            _isActive = false;
            onStop();
        }
    }

    private final void onStop()
    {
        restorePlayerData();
        afterRestoration();
    }
    
    //#region restoration
    private void restorePlayerData()
    {
        Console.debugRun(
            () -> Console.warning("BEGAN RESTORING PLAYER'S DATA"),
            () -> restoreIsOp(_isOp),
            () -> restoreIsWhitelisted(_isWhitelisted),
            () -> restoreCanPickupItems(_canPickupItems),
            () -> restoreIsCollidable(_isCollidable),
            () -> restorePermissionAttachments(_permissionAttachments),

            () -> restoreGameMode(_gameMode),

            () -> restoreScoreboard(_scoreboard),
            () -> restoreStatistics(_statistics),
            () -> restorePotionEffects(_potionEffects),
            () -> restoreAttributes(_attributes),

            () -> restoreItemUsageCooldowns(_itemUsageCooldowns),
            () -> restoreItemOnCursor(_itemOnCursor),
            () -> restoreItemInUseTicks(_itemInUseTicks),
            () -> restoreInventory(_inventoryContents),
            () -> restoreEnderChest(_enderChestContents),

            () -> restoreLocation(_location),
            () -> restoreLastDeathLocation(_lastDeathLocation),
            () -> restoreRespawnLocation(_respawnLocation),
            () -> restoreVelocity(_velocity),

            () -> restoreSpectatorTarget(_spectatorTarget),
            () -> restoreVehicle(_vehicle),
            () -> restorePassengers(_passengers),
            
            () -> restoreCustomName(_customName),
            () -> restoreDisplayName(_displayName),
            () -> restorePlayerListName(_playerListName),
            () -> restorePlayerListHeader(_playerListHeader),
            () -> restorePlayerListFooter(_playerListFooter),

            () -> restoreAllowFlight(_allowFlight),
            () -> restoreFlySpeed(_flySpeed),
            () -> restoreIsFlying(_isFlying),
            () -> restoreIsGliding(_isGliding),
            () -> restoreIsSneaking(_isSneaking),
            () -> restoreIsSprinting(_isSprinting),
            () -> restoreIsSwimming(_isSwimming),
            () -> restoreHasGravity(_hasGravity),

            () -> restoreIsGlowing(_isGlowing),
            () -> restoreIsInvisible(_isInvisible),
            () -> restoreIsInvulnerable(_isInvulnerable),
            () -> restoreIsSilent(_isSilent),
            () -> restoreSleepingIsIgnored(_sleepingIsIgnored),
            () -> restoreIsVisibleByDefault(_isVisibleByDefault),

            () -> restoreArrowsInBody(_arrowsInBody),
            () -> restoreArrowCooldown(_arrowCooldown),
            () -> restoreEnchantmentSeed(_enchantmentSeed),
            () -> restoreFoodLevel(_foodLevel),

            () -> restoreFireTicks(_fireTicks),
            () -> restoreHasVisualFire(_hasVisualFire),
            () -> restoreFreezeTicks(_freezeTicks),
            
            () -> restoreExpLevel(_expLevel),
            () -> restoreExp(_exp),
            () -> restoreExpCooldown(_expCooldown),
            () -> restoreTotalExperience(_totalExperience),
            
            () -> restoreRemainingAir(_remainingAir),
            () -> restoreMaximumAir(_maximumAir),

            () -> restoreNoActionTicks(_noActionTicks),
            () -> restoreNoDamageTicks(_noDamageTicks),
            () -> restoreMaximumNoDamageTicks(_maximumNoDamageTicks),
            () -> restorePortalCooldown(_portalCooldown),
            () -> restoreSaturatedRegenRate(_saturatedRegenRate),
            () -> restoreStarvationRate(_starvationRate),
            () -> restoreUnsaturatedRegenRate(_unsaturatedRegenRate),
            () -> restoreTicksLived(_ticksLived),

            () -> restoreSaturation(_saturation),
            () -> restoreExhaustion(_exhaustion),
            () -> restoreFallDistance(_fallDistance),
            () -> restoreWalkSpeed(_walkSpeed),
            () -> restoreAbsorptionAmount(_absorptionAmount),
            () -> restoreHealth(_health),
            () -> restoreHealthScale(_healthScale),
            () -> restoreHealthIsScaled(_healthIsScaled),
            () -> restoreLastDamage(_lastDamage),
            () -> Console.warning("FINISHED RESTORING PLAYER'S DATA")
        );
    }

    protected void restoreIsOp(boolean isOp)
    {
        player.setOp(isOp);
    }
    
    protected void restoreIsWhitelisted(boolean isWhitelisted)
    {
        player.setWhitelisted(isWhitelisted);
    }
    
    protected void restoreCanPickupItems(boolean canPickupItems)
    {
        player.setCanPickupItems(canPickupItems);
    }

    protected void restoreIsCollidable(boolean isCollidable)
    {
        player.setCollidable(isCollidable);
    }
    
    protected void restoreAllowFlight(boolean allowFlight)
    {
        player.setAllowFlight(allowFlight);
    }

    protected void restoreFlySpeed(float flySpeed)
    {
        player.setFlySpeed(flySpeed);
    }

    protected void restoreIsFlying(boolean isFlying)
    {
        player.setFlying(isFlying);
    }

    protected void restoreIsGlowing(boolean isGlowing)
    {
        player.setGlowing(isGlowing);
    }

    protected void restoreIsGliding(boolean isGliding)
    {
        player.setGliding(isGliding);
    }

    protected void restoreHealthIsScaled(boolean healthIsScaled)
    {
        player.setHealthScaled(healthIsScaled);
    }

    protected void restoreHasGravity(boolean hasGravity)
    {
        player.setGravity(hasGravity);
    }

    protected void restoreIsInvisible(boolean isInvisible)
    {
        player.setInvisible(isInvisible);
    }

    protected void restoreIsInvulnerable(boolean isInvulnerable)
    {
        player.setInvulnerable(isInvulnerable);
    }

    protected void restoreIsSilent(boolean isSilent)
    {
        player.setSilent(isSilent);
    }

    protected void restoreSleepingIsIgnored(boolean sleepingIsIgnored)
    {
        player.setSleepingIgnored(sleepingIsIgnored);
    }

    protected void restoreIsSneaking(boolean isSneaking)
    {
        player.setSneaking(isSneaking);
    }

    protected void restoreIsSprinting(boolean isSprinting)
    {
        player.setSprinting(isSprinting);
    }

    protected void restoreIsSwimming(boolean isSwimming)
    {
        player.setSwimming(isSwimming);
    }

    protected void restoreIsVisibleByDefault(boolean isVisibleByDefault)
    {
        player.setVisibleByDefault(isVisibleByDefault);
    }

    protected void restoreHasVisualFire(boolean hasVisualFire)
    {
        player.setVisualFire(hasVisualFire);
    }

    protected void restoreEnchantmentSeed(int enchantmentSeed)
    {
        player.setEnchantmentSeed(enchantmentSeed);
    }

    protected void restoreArrowCooldown(int arrowCooldown)
    {
        player.setArrowCooldown(arrowCooldown);
    }

    protected void restoreArrowsInBody(int arrowsInBody)
    {
        player.setArrowsInBody(arrowsInBody);
    }

    protected void restoreExpCooldown(int expCooldown)
    {
        player.setExpCooldown(expCooldown);
    }

    protected void restoreFoodLevel(int foodLevel)
    {
        player.setFoodLevel(foodLevel);
    }

    protected void restoreFireTicks(int fireTicks)
    {
        player.setFireTicks(fireTicks);
    }

    protected void restoreFreezeTicks(int freezeTicks)
    {
        player.setFreezeTicks(freezeTicks);
    }

    protected void restoreItemInUseTicks(int itemInUseTicks)
    {
        player.setItemInUseTicks(itemInUseTicks);
    }

    protected void restoreExpLevel(int expLevel)
    {
        player.setLevel(expLevel);
    }

    protected void restoreMaximumAir(int maximumAir)
    {
        player.setMaximumAir(maximumAir);
    }

    protected void restoreMaximumNoDamageTicks(int maximumNoDamageTicks)
    {
        player.setMaximumNoDamageTicks(maximumNoDamageTicks);
    }

    protected void restoreNoActionTicks(int noActionTicks)
    {
        player.setNoActionTicks(noActionTicks);
    }

    protected void restoreNoDamageTicks(int noDamageTicks)
    {
        player.setNoDamageTicks(noDamageTicks);
    }

    protected void restorePortalCooldown(int portalCooldown)
    {
        player.setPortalCooldown(portalCooldown);
    }

    protected void restoreRemainingAir(int remainingAir)
    {
        player.setRemainingAir(remainingAir);
    }

    protected void restoreSaturatedRegenRate(int saturatedRegenRate)
    {
        player.setSaturatedRegenRate(saturatedRegenRate);
    }

    protected void restoreStarvationRate(int starvationRate)
    {
        player.setStarvationRate(starvationRate);
    }

    protected void restoreUnsaturatedRegenRate(int unsaturatedRegenRate)
    {
        player.setUnsaturatedRegenRate(unsaturatedRegenRate);
    }

    protected void restoreTicksLived(int ticksLived)
    {
        player.setTicksLived(ticksLived);
    }

    protected void restoreTotalExperience(int totalExperience)
    {
        player.setTotalExperience(totalExperience);
    }

    protected void restoreSaturation(float saturation)
    {
        player.setSaturation(saturation);
    }

    protected void restoreExp(float exp)
    {
        player.setExp(exp);
    }

    protected void restoreExhaustion(float exhaustion)
    {
        player.setExhaustion(exhaustion);
    }

    protected void restoreFallDistance(float fallDistance)
    {
        player.setFallDistance(fallDistance);
    }

    protected void restoreWalkSpeed(float walkSpeed)
    {
        player.setWalkSpeed(walkSpeed);
    }

    protected void restoreAbsorptionAmount(double absorptionAmount)
    {
        player.setAbsorptionAmount(absorptionAmount);
    }

    protected void restoreHealth(double health)
    {
        player.setHealth(health);
    }

    protected void restoreHealthScale(double healthScale)
    {
        player.setHealthScale(healthScale);
    }

    protected void restoreLastDamage(double lastDamage)
    {
        player.setLastDamage(lastDamage);
    }

    protected void restoreGameMode(GameMode gameMode)
    {
        player.setGameMode(gameMode);
    }

    protected void restoreCustomName(String customName)
    {
        player.setCustomName(customName);
    }

    protected void restoreDisplayName(String displayName)
    {
        player.setDisplayName(displayName);
    }

    protected void restorePlayerListName(String playerListName)
    {
        player.setPlayerListName(playerListName);
    }

    protected void restorePlayerListHeader(String playerListHeader)
    {
        player.setPlayerListHeader(playerListHeader);
    }

    protected void restorePlayerListFooter(String playerListFooter)
    {
        player.setPlayerListFooter(playerListFooter);
    }

    protected void restoreLocation(Location location)
    {
        player.teleport(location);
    }

    protected void restoreLastDeathLocation(Location lastDeathLocation)
    {
        player.setLastDeathLocation(lastDeathLocation);
    }

    protected void restoreRespawnLocation(Location respawnLocation)
    {
        player.setRespawnLocation(respawnLocation);
    }

    protected void restoreVelocity(Vector velocity)
    {
        player.setVelocity(velocity);
    }

    protected void restoreItemOnCursor(ItemStack itemOnCursor)
    {
        player.setItemOnCursor(itemOnCursor);
    }

    protected void restoreInventory(ItemStack[] inventoryContents)
    {
        player.getInventory().setContents(inventoryContents);
    }

    protected void restoreEnderChest(ItemStack[] enderChestContents)
    {
        player.getEnderChest().setContents(enderChestContents);
    }

    protected void restoreSpectatorTarget(Entity spectatorTarget)
    {
        player.setSpectatorTarget(spectatorTarget);
    }

    protected void restoreVehicle(Entity vehicle)
    {
        if (vehicle != null)
        {
            vehicle.addPassenger(player);
        }
    }

    protected void restorePassengers(List<Entity> passengers)
    {
        passengers.forEach(Console.debugConsumer(player::addPassenger));
    }

    protected void restoreScoreboard(Scoreboard scoreboard)
    {
        player.setScoreboard(scoreboard);
    }

    protected void restoreItemUsageCooldowns(Map<Material, Integer> itemUsageCooldowns)
    {
        itemUsageCooldowns.forEach(Console.debug(player::setCooldown));
    }

    protected void restoreStatistics(Map<Statistic, Integer> statistics)
    {
        statistics.forEach(Console.debug(player::setStatistic));
    }

    protected void restorePermissionAttachments(List<PermissionAttachmentInfo> permissionAttachments)
    {
        clearPlayerPermissions();
        permissionAttachments.forEach(this::reattachPermissionInfo);
    }

    protected void reattachPermissionInfo(PermissionAttachmentInfo info)
    {
        PermissionAttachment attachment = info.getAttachment();
        if (attachment == null)
        {
            return;
        }

        Plugin plugin = attachment.getPlugin();
        String permission = info.getPermission();
        boolean value = info.getValue();
        player.addAttachment(plugin, permission, value);
    }

    protected void restorePotionEffects(List<PotionEffect> potionEffects)
    {
        clearPotionEffects();
        potionEffects.forEach(Console.debugConsumer(player::addPotionEffect));
    }

    protected void restoreAttributes(List<AttributeSubstitution> attributes)
    {
        attributes.forEach(Console.debug(AttributeSubstitution::restore));
    }

    protected void afterRestoration() { }
    //#endregion
}
