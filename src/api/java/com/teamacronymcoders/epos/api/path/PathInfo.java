package com.teamacronymcoders.epos.api.path;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PathInfo implements INBTSerializable<CompoundNBT>, Comparable<PathInfo> {
    private final String registryName;
    private final IPath path;
    private boolean active;
    private int level;
    private CompoundNBT data;

    public PathInfo(IPath path) {
        this.path = path;
        this.registryName = path.toString();
        this.data = new CompoundNBT();
    }

    @Override
    public int compareTo(PathInfo o) {
        return o.registryName.equals(this.registryName) && o.path == this.path && o.level == this.level ? 1 : 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("Active", active);
        nbt.putInt("Level", level);
        nbt.put("Data", data);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.active = nbt.getBoolean("Active");
        this.level = nbt.getInt("Level");
        this.data = nbt.getCompound("Data");
    }
}
