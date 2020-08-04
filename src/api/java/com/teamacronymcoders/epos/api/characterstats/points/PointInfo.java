package com.teamacronymcoders.epos.api.characterstats.points;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PointInfo implements INBTSerializable<CompoundNBT> {

    private PointType type;
    private int totalPoints;
    private int currentPoints;

    public PointInfo() {}

    public PointInfo(PointType type, int totalPoints, int currentPoints) {
        this.type = type;
        this.totalPoints = totalPoints;
        this.currentPoints = currentPoints;
    }

    public PointType getType() {
        return type;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public boolean addPoint() {
        totalPoints++;
        if (currentPoints + 1 > totalPoints) return false;
        currentPoints++;
        return true;
    }

    public boolean addPoints(int points) {
        totalPoints += points;
        if (currentPoints + points > totalPoints) return false;
        currentPoints += points;
        return true;
    }

    public boolean removePoint() {
        if (currentPoints - 1 < 0) return false;
        currentPoints--;
        return true;
    }

    public boolean removePoints(int points) {
        if (currentPoints - points < 0) return false;
        currentPoints -= points;
        return true;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("Type", type.getString());
        nbt.putInt(type.getCurrentNBT(), currentPoints);
        nbt.putInt(type.getTotalNBT(), totalPoints);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.type = PointType.getPointType(nbt.getString("type"));
        this.currentPoints = nbt.getInt(type.getCurrentNBT());
        this.totalPoints = nbt.getInt(type.getTotalNBT());
    }

    public static PointInfo createFromNBT(CompoundNBT nbt) {
        PointInfo info = new PointInfo();
        info.deserializeNBT(nbt);
        return info;
    }

}
