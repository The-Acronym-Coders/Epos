package com.teamacronymcoders.epos.utils;

import com.google.common.collect.Multimap;
import java.util.Collection;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.IAttribute;

public class AttributeUtils {

    public static double calculateValueFromModifiers(Multimap<String, AttributeModifier> attributeModifiers, IAttribute attribute) {
        return calculateValueFromModifiers(attributeModifiers, attribute, 0);
    }

    public static double calculateValueFromModifiers(Multimap<String, AttributeModifier> attributeModifiers, IAttribute attribute, double initialValue) {
        Collection<AttributeModifier> modifiers = attributeModifiers.get(attribute.getName());
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