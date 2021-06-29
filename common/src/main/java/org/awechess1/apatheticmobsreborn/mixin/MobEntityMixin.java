package org.awechess1.apatheticmobsreborn.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.awechess1.apatheticmobsreborn.ApatheticMobsRebornMod;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject( method = "setTarget", at = @At("HEAD"), cancellable = true)
    public void onlyTargetNemesis(@Nullable LivingEntity target, CallbackInfo ci) {
        if(target instanceof PlayerEntity) {
            if(!ApatheticMobsRebornMod.canTakeRevengeOnPlayer(this, (PlayerEntity)target)) {
                ci.cancel();
            }
        }
    }
}
