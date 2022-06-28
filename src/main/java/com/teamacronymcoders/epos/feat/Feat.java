package com.teamacronymcoders.epos.feat;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.registry.FeatRegistrar;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Feat implements IFeat {

    private final NonNullSupplier<MutableComponent> name;
    private final NonNullSupplier<MutableComponent> description;
    private final NonNullSupplier<Boolean> isAbility;

    private final NonNullSupplier<List<EventManager.ISubscribe>> subscribables;
    @Nullable
    private final NonNullSupplier<Codec<? extends IFeat>> featCodec;

    private String translationKey;

    /**
     * Constructor for Feat(s).
     *
     * @param name        The Name of the Feat.
     * @param description The Description of the Feat.
     * @param isAbility   If the Feat is an Active 'Ability'.
     */
    public Feat(MutableComponent name, MutableComponent description, boolean isAbility) {
        this.name = () -> name;
        this.description = () -> description;
        this.isAbility = () -> isAbility;
        this.subscribables = ArrayList::new;
        this.featCodec = null;
    }

    /**
     * Constructor for Feat(s).
     *
     * @param name        The Name of the Feat.
     * @param description The Description of the Feat.
     * @param isAbility   If the Feat is an Active 'Ability'.
     */
    public Feat(MutableComponent name, MutableComponent description, boolean isAbility, List<EventManager.ISubscribe> subscribables, Codec<? extends IFeat> featCodec) {
        this.name = () -> name;
        this.description = () -> description;
        this.isAbility = () -> isAbility;
        if (subscribables == null) {
            this.subscribables = ArrayList::new;
        } else {
            this.subscribables = () -> subscribables;
        }
        if (featCodec == null) {
            this.featCodec = FeatRegistrar.DEFAULT_FEAT_SERIALIZER;
        } else {
            this.featCodec = () -> featCodec;
        }
    }

    /**
     * Constructor for Feat(s).
     *
     * @param name        The Name of the Feat.
     * @param description The Description of the Feat.
     * @param isAbility   If the Feat is an Active 'Ability'.
     */
    public Feat(NonNullSupplier<MutableComponent> name, NonNullSupplier<MutableComponent> description, NonNullSupplier<Boolean> isAbility, List<EventManager.ISubscribe> subscribables, NonNullSupplier<Codec<? extends IFeat>> featCodec) {
        this.name = name;
        this.description = description;
        this.isAbility = isAbility;
        if (subscribables == null) {
            this.subscribables = ArrayList::new;
        } else {
            this.subscribables = () -> subscribables;
        }
        this.featCodec = featCodec;
    }

    @Override
    public MutableComponent getName() {
        return this.name.get();
    }

    @Override
    public MutableComponent getDescription() {
        return this.description.get();
    }

    @Override
    public @NotNull String getTranslationKey(ResourceLocation id) {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("feat", id);
        }
        return this.translationKey;
    }

    @Override
    public boolean isAbility() {
        return this.isAbility.get();
    }

    @Override
    public Codec<? extends IFeat> codec() {
        return FeatRegistrar.DEFAULT_FEAT_SERIALIZER.get();
    }


}
