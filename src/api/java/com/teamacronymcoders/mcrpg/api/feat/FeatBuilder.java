package com.teamacronymcoders.eposmajorum.api.feat;

import com.google.common.collect.Lists;
import com.teamacronymcoders.eposmajorum.api.characterstats.ICharacterStats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.Event;
import org.apache.logging.log4j.util.TriConsumer;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class FeatBuilder {
    private final ResourceLocation registryName;
    private final ITextComponent name;
    private final ITextComponent description;
    private final List<FeatEventHandler<?>> eventHandlers;

    private FeatBuilder(@Nonnull ResourceLocation registryName, ITextComponent name,
                        ITextComponent description, List<FeatEventHandler<?>> eventHandlers) {
        this.registryName = registryName;
        this.name = name;
        this.description = description;
        this.eventHandlers = eventHandlers;
    }

    public static FeatBuilder start(@Nonnull ResourceLocation registryName) {
        return new FeatBuilder(registryName, nameToComponent(registryName, "name"),
                nameToComponent(registryName, "description"), Lists.newArrayList());
    }

    private static ITextComponent nameToComponent(ResourceLocation registryName, String append) {
        return new TranslationTextComponent("feat." + registryName.getNamespace() +
                "." + registryName.getPath() + "." + append);
    }

    public FeatBuilder withName(ITextComponent newName) {
        return new FeatBuilder(registryName, newName, description, eventHandlers);
    }

    public FeatBuilder withDescription(ITextComponent newDescription) {
        return new FeatBuilder(registryName, name, newDescription, eventHandlers);
    }

    public <T extends Event> FeatBuilder withEventHandler(Class<T> eventClass,
                                                          TriConsumer<T, LivingEntity, ICharacterStats> eventHandler) {
        eventHandlers.add(new FeatEventHandler<>(registryName, eventClass, eventHandler));
        return new FeatBuilder(registryName, name, description, eventHandlers);
    }

    public Feat finish() {
        return new Feat(registryName, name, description, eventHandlers);
    }
}
