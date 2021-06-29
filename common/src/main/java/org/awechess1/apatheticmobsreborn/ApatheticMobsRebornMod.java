package org.awechess1.apatheticmobsreborn;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;


public class ApatheticMobsRebornMod {
    public static final String MOD_ID = "apatheticmobsreborn";

    static ModConfig cfg = null;

    public static ModConfig getConfig() {
        if(cfg == null)
            cfg = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        return cfg;
    }
    public static void init() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
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
        return ((VengefulLivingEntity)entity).getPlayersToTakeRevengeOn().contains(player.getUuid());
    }
}
