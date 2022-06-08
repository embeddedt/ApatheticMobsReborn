package org.awechess1.apatheticmobsreborn.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.awechess1.apatheticmobsreborn.ApatheticMobsRebornMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UpdateAttackTargetTask.class)
public class UpdateAttackTargetTaskMixin {
    @Inject(method = "updateAttackTarget", at = @At("HEAD"), cancellable = true)
    private static <E extends MobEntity> void cancelAttackingIfNotVengeful(E mob, LivingEntity livingEntity, CallbackInfo ci) {
        if(!ApatheticMobsRebornMod.considerMobForApatheticness(mob))
            return;
        if(livingEntity instanceof PlayerEntity) {
            if(!ApatheticMobsRebornMod.canTakeRevengeOnPlayer(mob, (PlayerEntity)livingEntity)) {
                ci.cancel();
            }
        }
    }
}
