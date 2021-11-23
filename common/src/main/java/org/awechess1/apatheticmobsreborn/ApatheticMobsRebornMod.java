package org.awechess1.apatheticmobsreborn;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class ApatheticMobsRebornMod {
    public static final String MOD_ID = "apatheticmobsreborn";

    public static ConfigHolder<ModConfig> cfgHolder = null;
    
    public static Set<UUID> playerUUIDs;
    
    private static ActionResult recomputeCachedConfigValues(ModConfig data) {
        try {
            playerUUIDs = data.playerList.stream().map(UUID::fromString).collect(Collectors.toSet());
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            return ActionResult.FAIL;
        }
        return ActionResult.SUCCESS;
    }

    public static ModConfig getConfig() {
        return cfgHolder.getConfig();
    }
    public static void init() {
        cfgHolder = AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        cfgHolder.registerSaveListener((manager, data) -> recomputeCachedConfigValues(data));
        cfgHolder.registerLoadListener((manager, data) -> recomputeCachedConfigValues(data));
        recomputeCachedConfigValues(cfgHolder.getConfig());
    }

    /**
     * Checks whether an entity should act apathetic, excluding the revenge case.
     *
     * If the difficulty or entity type is blacklisted, returns false. Otherwise, returns true.
     * An exception is made for the ender dragon, which is never apathetic (to allow collecting dragon's breath).
     * @param entity An entity to check
     * @return
     */
    public static boolean considerMobForApatheticness(LivingEntity entity) {
        if(entity.world.isClient) {
            return false;
        }
        if(getConfig().blacklistDifficulties.contains(entity.world.getServer().getSaveProperties().getDifficulty().getName()))
            return false;
        if(getConfig().blacklistMobs.contains(Registry.ENTITY_TYPE.getKey(entity.getType()).toString()))
            return false;
        return !(entity instanceof EnderDragonEntity);
    }

    public static boolean canTakeRevengeOnPlayer(LivingEntity entity, PlayerEntity player) {
        ModConfig cfg = getConfig();
        boolean playerInList = playerUUIDs.contains(player.getUuid());
        if(cfg.isPlayerListBlacklist && playerInList)
            return true; /* player is blacklisted from apathetic treatment, always be apathetic towards them */
        else if(!cfg.isPlayerListBlacklist && !playerInList)
            return true; /* player is not in whitelist for apathetic treatment, always be apathetic towards them */
        return ((VengefulLivingEntity)entity).getPlayersToTakeRevengeOn().contains(player.getUuid());
    }
}
