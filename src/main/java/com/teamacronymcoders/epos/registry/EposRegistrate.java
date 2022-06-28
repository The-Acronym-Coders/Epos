/*
 * MIT License
 *
 * Copyright (c) 2019-2021 Team Acronym Coders
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.teamacronymcoders.epos.registry;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.feat.Feat;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.ISpiritualAid;
import com.teamacronymcoders.epos.path.Path;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.registry.builder.FeatBuilder;
import com.teamacronymcoders.epos.registry.builder.PathBuilder;
import com.teamacronymcoders.epos.registry.builder.SkillBuilder;
import com.teamacronymcoders.epos.skill.Skill;
import com.teamacronymcoders.epos.util.EposRegistries;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class EposRegistrate extends AbstractRegistrate<EposRegistrate> {

    // Path
    private static final NonNullBiFunction<String, BuilderCallback, PathBuilder<Path, EposRegistrate>> PATH = PathBuilder.entry(() -> Epos.instance().getRegistrate());
    //private static final NonNullBiFunction<String, BuilderCallback, FeatBuilder<AbstractPathFeature, EposRegistrate>> PATH_FEATURE = FeatBuilder.entry(() -> Epos.instance().getRegistrate());

    // Skill
    private static final NonNullBiFunction<String, BuilderCallback, SkillBuilder<Skill, EposRegistrate>> SKILL = SkillBuilder.entry(() -> Epos.instance().getRegistrate());

    // Feat
    private static final NonNullBiFunction<String, BuilderCallback, FeatBuilder<Feat, EposRegistrate>> FEAT = FeatBuilder.entry(() -> Epos.instance().getRegistrate());
    //private static final NonNullBiFunction<String, BuilderCallback, FeatBuilder<FeatInfo, EposRegistrate>> FEAT_INFO = FeatBuilder.entry(() -> Epos.instance().getRegistrate());

    public static EposRegistrate create(String modid) {
        return new EposRegistrate(modid).registerEventListeners(FMLJavaModLoadingContext.get().getModEventBus());
    }

    protected EposRegistrate(String modid) {
        super(modid);
        }

    public IForgeRegistry<Codec<? extends IPath>> getPathSerializerRegistry() {
        return EposRegistries.PATH_SERIALIZERS.get();
    }

    public IForgeRegistry<Codec<? extends ISkill>> getSkillSerializerRegistry() {
        return EposRegistries.SKILL_SERIALIZERS.get();
    }

    public IForgeRegistry<Codec<? extends IFeat>> getFeatSerializerRegistry() {
        return EposRegistries.FEAT_SERIALIZERS.get();
    }

    public IForgeRegistry<Codec<? extends IPathFeature>> getPathFeatureSerializerRegistry() {
        return EposRegistries.PATH_FEATURE_SERIALIZERS.get();
    }

    public IForgeRegistry<ISpiritualAid> getSpiritualAidSerializerRegistry() {
        return EposRegistries.SPIRITUAL_AIDS.get();
    }

    public IForgeRegistry<FeatInfo> getFeatInfoRegistry() {
        return EposRegistries.FEAT_INFOS.get();
    }



    // Path
    public PathBuilder<Path, EposRegistrate> path(String name) {
        return Epos.instance().getRegistrate().object(name).entry(PATH);
    }

    public RegistryEntry<Codec<? extends IPath>> pathSerializer(String name, NonNullSupplier<Codec<? extends IPath>> codecSupplier) {
        return Epos.instance().getRegistrate().simple(name, EposRegistries.Keys.PATH_SERIALIZERS, codecSupplier);
    }



    // Path Feature
    public FeatBuilder<Feat, EposRegistrate> pathFeature(String name) {
        return Epos.instance().getRegistrate().object(name).entry(FEAT);
    }

    public RegistryEntry<Codec<? extends IPathFeature>> pathFeatureSerializer(String name, NonNullSupplier<Codec<? extends IPathFeature>> codecSupplier) {
        return Epos.instance().getRegistrate().simple(name, EposRegistries.Keys.PATH_FEATURE_SERIALIZERS, codecSupplier);
    }



    // Skill
    public SkillBuilder<Skill, EposRegistrate> skill(String name) {
        return Epos.instance().getRegistrate().object(name).entry(SKILL);
    }

    public RegistryEntry<Codec<? extends ISkill>> skillSerializer(String name, NonNullSupplier<Codec<? extends ISkill>> codecSupplier) {
        return Epos.instance().getRegistrate().simple(name, EposRegistries.Keys.SKILL_SERIALIZERS, codecSupplier);
    }



    // Feat
    public FeatBuilder<Feat, EposRegistrate> feat(String name) {
        return Epos.instance().getRegistrate().object(name).entry(FEAT);
    }

    public FeatBuilder<Feat, EposRegistrate> featInfo(String name) {
        return Epos.instance().getRegistrate().object(name).entry(FEAT);
    }

    public RegistryEntry<Codec<? extends IFeat>> featSerializer(String name, NonNullSupplier<Codec<? extends IFeat>> codecSupplier) {
        return Epos.instance().getRegistrate().simple(name, EposRegistries.Keys.FEAT_SERIALIZERS, codecSupplier);
    }



    // Spiritual Aid
    public FeatBuilder<Feat, EposRegistrate> spiritualAid(String name) {
        return Epos.instance().getRegistrate().object(name).entry(FEAT);
    }

    public RegistryEntry<Codec<? extends ISpiritualAid>> spiritualAidSerializer(String name, NonNullSupplier<Codec<? extends ISpiritualAid>> codecSupplier) {
        return Epos.instance().getRegistrate().simple(name, EposRegistries.Keys.SPIRITUAL_AID_SERIALIZERS, codecSupplier);
    }
}
