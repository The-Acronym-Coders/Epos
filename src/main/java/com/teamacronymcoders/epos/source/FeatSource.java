package com.teamacronymcoders.epos.source;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.source.Source;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.Optional;

public class FeatSource extends Source {
    private ResourceLocation featName;
    private ResourceLocation featParentName;
    private boolean requiresPoint;

    public FeatSource() {

    }

    public FeatSource(ResourceLocation featName, ResourceLocation featParentName, boolean requiresPoint) {
        this.featName = featName;
        this.featParentName = featParentName;
        this.requiresPoint = requiresPoint;
    }

    @Override
    public boolean isValid(ICharacterStats stats, LivingEntity character) {
        return stats.getFeats().hasFeat(featName);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("featName", this.featName.toString());
        nbt.putBoolean("requiresPoints", this.requiresPoint);
        Optional.ofNullable(this.featParentName)
                .map(ResourceLocation::toString)
                .ifPresent(parentName -> nbt.putString("featParentName", parentName));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.featName = new ResourceLocation(nbt.getString("featName"));
        this.requiresPoint = nbt.getBoolean("requiresPoints");
        if (nbt.contains("featParentName")) {
            this.featParentName = new ResourceLocation(nbt.getString("featParentName"));
        }
    }
}
