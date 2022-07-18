package org.awechess1.apatheticmobsreborn.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.awechess1.apatheticmobsreborn.ApatheticMobsRebornMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TargetPredicate.class)
public class TargetPredicateMixin {
    @Inject(at=@At("HEAD"), method= "test(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/LivingEntity;)Z", cancellable = true)
    public void testApatheticness(LivingEntity baseEntity, LivingEntity targetEntity, CallbackInfoReturnable<Boolean> cir) {
        if(baseEntity != targetEntity && baseEntity != null && targetEntity instanceof PlayerEntity) {
            /* Check if mob is a non-peaceful mob and is allowed to be apathetic */
            if(baseEntity instanceof MobEntity && (((MobEntityAccessor)baseEntity).invokeDisallowedInPeaceful()||ApatheticMobsRebornMod.isHostileOverride(baseEntity)) && ApatheticMobsRebornMod.considerMobForApatheticness(baseEntity)) {
                /* Check if taking revenge on the player is allowed */
                if(ApatheticMobsRebornMod.canTakeRevengeOnPlayer(baseEntity, (PlayerEntity)targetEntity)) {
                    return; /* Allow vanilla behavior */
                }
                /* Otherwise, block targetting this player */
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
