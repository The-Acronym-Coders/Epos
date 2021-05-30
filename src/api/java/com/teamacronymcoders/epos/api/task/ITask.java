package com.teamacronymcoders.epos.api.task;

import com.teamacronymcoders.epos.api.task.goal.IGoal;
import com.teamacronymcoders.epos.api.task.reward.IReward;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface ITask extends INBTSerializable<CompoundNBT> {
    ResourceLocation getID();
    List<IGoal> getGoals();
    List<IReward> getRewards();
}
