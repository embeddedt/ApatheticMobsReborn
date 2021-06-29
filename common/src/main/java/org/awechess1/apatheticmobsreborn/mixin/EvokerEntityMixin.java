package org.awechess1.apatheticmobsreborn.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EvokerEntity.class)
public class EvokerEntityMixin {
    @Redirect(method = "initGoals", at = @At( value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V"))
    public void skipAddingFleeEntityGoal(GoalSelector goalSelector, int priority, Goal goal) {
        if(goal instanceof FleeEntityGoal) {
            Class<? extends LivingEntity> clz = ((FleeEntityGoalMixin<? extends LivingEntity>)goal).getEntityClassToFleeFrom();
            if(PlayerEntity.class.isAssignableFrom(clz)) {
                return;
            }
        }
        goalSelector.add(priority, goal);
    }
}
