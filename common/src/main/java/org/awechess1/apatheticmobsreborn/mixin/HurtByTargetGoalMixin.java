package org.awechess1.apatheticmobsreborn.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.awechess1.apatheticmobsreborn.ApatheticMobsRebornMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RevengeGoal.class)
public abstract class HurtByTargetGoalMixin extends TrackTargetGoal {
    public HurtByTargetGoalMixin(MobEntity mob, boolean b) {
        super(mob, b);
    }

    @Inject(method = "setMobEntityTarget", at = @At("HEAD"), cancellable = true)
    protected void handleRevengeSetTarget(MobEntity mobToAlert, LivingEntity entityToAttack, CallbackInfo ci) {
        if(mobToAlert.world.isClient)
            return;
        if(entityToAttack instanceof PlayerEntity && !ApatheticMobsRebornMod.canTakeRevengeOnPlayer(mobToAlert, (PlayerEntity)entityToAttack)) {
            ci.cancel();
        }
    }
}
