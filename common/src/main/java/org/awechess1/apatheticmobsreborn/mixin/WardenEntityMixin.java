package org.awechess1.apatheticmobsreborn.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.WardenAngerManager;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.awechess1.apatheticmobsreborn.ApatheticMobsRebornMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin extends LivingEntity {
    protected WardenEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isValidTarget", at = @At("HEAD"), cancellable = true)
    private void makeApatheticPlayersInvalid(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(!ApatheticMobsRebornMod.considerMobForApatheticness(this))
            return;
        if(entity instanceof PlayerEntity player && !ApatheticMobsRebornMod.canTakeRevengeOnPlayer(this, player)) {
            cir.setReturnValue(false);
        }
    }
}
