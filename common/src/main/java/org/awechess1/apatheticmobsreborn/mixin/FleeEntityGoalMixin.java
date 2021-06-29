package org.awechess1.apatheticmobsreborn.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FleeEntityGoal.class)
public interface FleeEntityGoalMixin<T extends LivingEntity> {
    @Accessor("classToFleeFrom")
    Class<T> getEntityClassToFleeFrom();
}
