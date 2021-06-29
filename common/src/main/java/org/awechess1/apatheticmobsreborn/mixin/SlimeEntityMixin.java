package org.awechess1.apatheticmobsreborn.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;
import org.awechess1.apatheticmobsreborn.ApatheticMobsRebornMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends LivingEntity {
    protected SlimeEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author embeddedt
     * Prevent slimes from damaging players.
     */
    @Inject( method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    public void ignorePlayerCollision(CallbackInfo ci) {
        if(!ApatheticMobsRebornMod.considerMobForApatheticness(this))
            return;
        ci.cancel();
    }
}
