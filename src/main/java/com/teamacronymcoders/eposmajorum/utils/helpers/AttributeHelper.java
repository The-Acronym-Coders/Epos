package com.teamacronymcoders.eposmajorum.utils.helpers;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.Collection;

public class AttributeHelper {
    public static void handleAttributes(Collection<AttributeModifier> modifiers, String skill, AttributeModifier newValue) {
        for (AttributeModifier modifier : modifiers) {
            if (modifier.getName().equals(skill)) {
                if (!modifier.equals(newValue)) {
                    modifiers.remove(modifier);
                    modifiers.add(newValue);
                }
                break;
            }
        }
    }
}
