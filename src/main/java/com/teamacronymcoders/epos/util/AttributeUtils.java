package com.teamacronymcoders.epos.util;

import com.google.common.collect.Multimap;
import java.util.Collection;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;

public class AttributeUtils {

    public static double calculateValueFromModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers, Attribute attribute) {
        return calculateValueFromModifiers(attributeModifiers, attribute, 0);
    }

    public static double calculateValueFromModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers, Attribute attribute, double initialValue) {
        Collection<AttributeModifier> modifiers = attributeModifiers.get(attribute);
        for (AttributeModifier modifier : modifiers) {
            Operation operation = modifier.getOperation();
            switch (operation) {
                case ADDITION:
                    initialValue += modifier.getAmount();
                    break;
                case MULTIPLY_BASE:
                    //TODO: Figure out how we should handle MULTIPLY_BASE so that we have the correct value
                    //attribute.getDefaultValue() * modifier.getAmount();
                    break;
                case MULTIPLY_TOTAL:
                    initialValue *= modifier.getAmount();
                    break;
            }
        }
        return initialValue;
    }
}