package org.awechess1.apatheticmobsreborn.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.awechess1.apatheticmobsreborn.ApatheticMobsRebornMod;
import org.awechess1.apatheticmobsreborn.VengefulLivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements VengefulLivingEntity {
    @Shadow @Nullable private LivingEntity attacker;
    @Shadow @Nullable protected PlayerEntity attackingPlayer;
    @Unique
    private final Set<UUID> apatheticMobs$playersToTakeRevengeOn = new HashSet<>();

    public Set<UUID> getPlayersToTakeRevengeOn() {
        return apatheticMobs$playersToTakeRevengeOn;
    }

    public LivingEntityMixin(EntityType<?> entityType, World level) {
        super(entityType, level);
    }

    /**
     * @author embeddedt
     * Saves the revenge data to the entity.
     */
    @Inject(method = "writeCustomDataToTag", at = @At(value = "TAIL"))
    public void writeRevengeData(CompoundTag compoundTag, CallbackInfo ci) {
        if (!this.apatheticMobs$playersToTakeRevengeOn.isEmpty()) {
            ListTag listTag = new ListTag();

            for (UUID uuid : apatheticMobs$playersToTakeRevengeOn) {
                CompoundTag uuidTag = new CompoundTag();
                uuidTag.putUuid("UUID", uuid);
                listTag.add(uuidTag);
            }

            compoundTag.put("ApatheticMobs_PlayerRevenge", listTag);
        }
    }

    /**
     * @author embeddedt
     * Loads the revenge data from the entity.
     */
    @Inject(method = "readCustomDataFromTag", at = @At(value = "TAIL"))
    public void readRevengeData(CompoundTag compoundTag, CallbackInfo ci) {
        if (compoundTag.contains("ApatheticMobs_PlayerRevenge", 9) && this.world != null && !this.world.isClient) {
            ListTag revengeListTag = compoundTag.getList("ApatheticMobs_PlayerRevenge", 10);
            for(int i = 0; i < revengeListTag.size(); ++i) {
                CompoundTag uuidTag = revengeListTag.getCompound(i);
                UUID uuid = uuidTag.getUuid("UUID");
                if(uuid != null)
                    apatheticMobs$playersToTakeRevengeOn.add(uuid);
            }
        }
    }

    /**
     * @author embeddedt
     * Blocks the entity from immediately targetting the player unless revenge is enabled.
     */
    @Inject(method = "damage", at = @At(value = "RETURN"))
    public void triggerPlayerHurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if(!ApatheticMobsRebornMod.considerMobForApatheticness((LivingEntity) (Object)this))
            return;
        if(cir.getReturnValue()) {
            Entity damagingEntity = damageSource.getAttacker();
            if (damagingEntity instanceof PlayerEntity) {
                if (ApatheticMobsRebornMod.getConfig().takeRevenge) {
                    apatheticMobs$playersToTakeRevengeOn.add(damagingEntity.getUuid());
                } else {
                    /* Inhibit any ability to trigger goals */
                    this.attacker = null;
                    this.attackingPlayer = null;
                }
            }
        }
    }

    /**
     * @author embeddedt
     * Prevents mobs from immediately aggroing on the player when within range. This essentially causes the mobs
     * to treat the player as though they're in creative mode.
     */
    @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("RETURN"), cancellable = true)
    private void checkApatheticBeforeAttack(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if(!ApatheticMobsRebornMod.considerMobForApatheticness((LivingEntity) (Object)this))
            return;
        if(cir.getReturnValue()) {
            if(livingEntity instanceof PlayerEntity && !apatheticMobs$playersToTakeRevengeOn.contains(livingEntity.getUuid()))
                cir.setReturnValue(false);
        }
    }

    /**
     * @author embeddedt
     * See above.
     */
    @Inject(method = "isTarget", at = @At("RETURN"), cancellable = true)
    private void checkApatheticBeforeAttack(LivingEntity livingEntity, TargetPredicate targetingConditions, CallbackInfoReturnable<Boolean> cir) {
        if(!ApatheticMobsRebornMod.considerMobForApatheticness((LivingEntity) (Object)this))
            return;
        if(cir.getReturnValue()) {
            if(livingEntity instanceof PlayerEntity && !apatheticMobs$playersToTakeRevengeOn.contains(livingEntity.getUuid()))
                cir.setReturnValue(false);
        }
    }
}