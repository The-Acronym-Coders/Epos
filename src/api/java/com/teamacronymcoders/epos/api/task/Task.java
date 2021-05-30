package com.teamacronymcoders.epos.api.task;

import com.teamacronymcoders.epos.api.task.goal.Goal;
import com.teamacronymcoders.epos.api.task.goal.IGoal;
import com.teamacronymcoders.epos.api.task.reward.IReward;
import com.teamacronymcoders.epos.api.task.reward.Reward;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Constants.NBT;

import java.util.ArrayList;
import java.util.List;

public class Task implements ITask {

    private ResourceLocation id;
    private final List<IGoal> goals;
    private final List<IReward> rewards;

    public Task(ResourceLocation id) {
        this.id = id;
        this.goals = new ArrayList<>();
        this.rewards = new ArrayList<>();
    }

    @Override
    public ResourceLocation getID() {
        return this.id;
    }

    @Override
    public List<IGoal> getGoals() {
        return this.goals;
    }

    @Override
    public List<IReward> getRewards() {
        return this.rewards;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT taskNBT = new CompoundNBT();
        ListNBT goalNBT = new ListNBT();
        ListNBT rewardNBT = new ListNBT();

        for (IGoal goal : this.getGoals()) {
            goalNBT.add(goal.serializeNBT());
        }

        for (IReward reward : this.getRewards()) {
            rewardNBT.add(reward.serializeNBT());
        }

        taskNBT.putString("taskID", this.getID().toString());
        taskNBT.put("taskGoals", goalNBT);
        taskNBT.put("taskRewards", rewardNBT);

        return taskNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        // Deserialize Task ID
        this.id = new ResourceLocation(nbt.getString("taskID"));

        // Deserialize Task Goals
        ListNBT goals = nbt.getList("taskGoals", NBT.TAG_COMPOUND);
        this.goals.clear();
        for (net.minecraft.nbt.INBT serializedGoal : goals) {
            Goal goal = new Goal();
            goal.deserializeNBT((CompoundNBT) serializedGoal);
            this.goals.add(goal);
        }

        // Deserialize Task Rewards
        ListNBT rewards = nbt.getList("taskRewards", NBT.TAG_COMPOUND);
        this.rewards.clear();
        for (net.minecraft.nbt.INBT serializedReward : rewards) {
            Reward reward = new Reward();
            reward.deserializeNBT((CompoundNBT) serializedReward);
            this.rewards.add(reward);
        }
    }
}
