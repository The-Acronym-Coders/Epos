package com.teamacronymcoders.epos.util;

import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class AttributeUtils {

    public static double calculateValueFromModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers, Attribute attribute) {
        return calculateValueFromModifiers(attributeModifiers, attribute, 0);
    }

    /**
     * Based on ModifiableAttributeInstance#computeValue.
     */
    public static double calculateValueFromModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers, Attribute attribute, double initialValue) {
        Collection<AttributeModifier> modifiers = attributeModifiers.get(attribute);
        if (modifiers.isEmpty()) {
            return attribute.clampValue(initialValue);
        }
        List<AttributeModifier> baseMultiplicationModifiers = new ArrayList<>();
        List<AttributeModifier> totalMultiplicationModifiers = new ArrayList<>();
        for (AttributeModifier modifier : modifiers) {
            switch (modifier.getOperation()) {
                case ADDITION:
                    initialValue += modifier.getAmount();
                    break;
                case MULTIPLY_BASE:
                    baseMultiplicationModifiers.add(modifier);
                    break;
                case MULTIPLY_TOTAL:
                    totalMultiplicationModifiers.add(modifier);
                    break;
            }
        }
        double value = initialValue;
        for (AttributeModifier modifier : baseMultiplicationModifiers) {
            value += initialValue * modifier.getAmount();
        }
        for (AttributeModifier modifier : totalMultiplicationModifiers) {
            value *= 1.0D + modifier.getAmount();
        }
        return attribute.clampValue(value);
    }
}