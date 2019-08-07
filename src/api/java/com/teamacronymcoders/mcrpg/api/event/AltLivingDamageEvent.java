package com.teamacronymcoders.eposmajorum.api.event;

import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class AltLivingDamageEvent extends LivingDamageEvent {
    private final float baseAmount;
    private final LivingDamageEvent event;

    public AltLivingDamageEvent(LivingDamageEvent event) {
        super(event.getEntityLiving(), event.getSource(), event.getAmount());
        baseAmount = event.getAmount();
        this.event = event;
    }

    public void addMultiplierToBase(float multiplier) {
        this.setAmount(this.getBaseAmount() + (baseAmount * multiplier));
    }

    public void addMultiplierToCurrent(float multiplier) {
        this.setAmount(this.getAmount() * multiplier);
    }

    public float getBaseAmount() {
        return this.baseAmount;
    }

    @Override
    public void setAmount(float amount) {
        this.event.setAmount(amount);
    }
}
