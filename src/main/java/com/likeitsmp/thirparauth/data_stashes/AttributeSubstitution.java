package com.likeitsmp.thirparauth.data_stashes;

import java.util.Collection;

import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;

final class AttributeSubstitution
{
    private final AttributeInstance attribute;
    private final double baseValue;
    private final Collection<AttributeModifier> modifiers;

    public AttributeSubstitution(AttributeInstance attribute)
    {
        this.attribute = attribute;
        baseValue = attribute.getBaseValue();
        modifiers = attribute.getModifiers();
    }

    public void restore()
    {
        attribute.setBaseValue(baseValue);
        modifiers.forEach(attribute::addModifier);
    }
}
