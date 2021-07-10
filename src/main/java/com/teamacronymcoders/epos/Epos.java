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

package com.teamacronymcoders.epos;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.teamacronymcoders.epos.api.capability.EposCapabilities;
import com.teamacronymcoders.epos.api.character.CharacterSheet;
import com.teamacronymcoders.epos.api.character.capability.CharacterSheetCapabilityProvider;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.SkillSerializer;
import com.teamacronymcoders.epos.client.EposClientHandler;
import com.teamacronymcoders.epos.registry.EposRegistrate;
import com.teamacronymcoders.epos.registry.SkillRegistrar;
import com.teamacronymcoders.epos.skill.Skill;
import net.ashwork.dynamicregistries.DynamicRegistryManager;
import net.ashwork.dynamicregistries.event.DynamicRegistryEvent;
import net.ashwork.dynamicregistries.registry.DynamicRegistry;
import net.ashwork.dynamicregistries.registry.DynamicRegistryBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;


@Mod(Epos.ID)
public class Epos {

    @VisibleForTesting
    public static final boolean IS_TESTING = true;

    public static final String ID = "epos";
    private static Epos instance;
    private final EposRegistrate registrate;
    private SimpleChannel network;
    private static final Logger LOGGER = LogManager.getLogger(ID);

    public Epos() {
        instance = this;
        this.registrate = EposRegistrate.create(ID);
        SkillRegistrar.register();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus(), forgeBus = MinecraftForge.EVENT_BUS;
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new EposClientHandler(modBus, forgeBus));
        modBus.addListener(this::setup);

        // Caps
        forgeBus.addGenericListener(Entity.class, this::setupCharacterSheetCaps);
        forgeBus.addListener(this::respawnCharacterSheetCaps);

        // Uncomment for test cases to run
        if (IS_TESTING) {
            this.addTestCases(modBus, forgeBus);
        } else {
            modBus.addListener(this::setupRegistries);

        }
    }

    public static final Epos instance() {
        return instance;
    }

    public EposRegistrate getRegistrate() {
        return this.registrate;
    }

    public SimpleChannel getNetwork() {
        return this.network;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    // Caps
    private void setupCharacterSheetCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity)
            event.addCapability(EposCapabilities.SHEET_ID, new CharacterSheetCapabilityProvider((LivingEntity) event.getObject()));
    }

    private void respawnCharacterSheetCaps(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(EposCapabilities.CHARACTER_SHEET).ifPresent(cap ->
                event.getPlayer().getCapability(EposCapabilities.CHARACTER_SHEET).ifPresent(c -> c.deserializeNBT(cap.serializeNBT())));
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(CharacterSheet.class, null, CharacterSheet::new);

            this.network = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Epos.ID, "network"))
                    .clientAcceptedVersions(str -> true).serverAcceptedVersions(str -> true)
                    .networkProtocolVersion(() -> Epos.ID + ":1").simpleChannel();
        });
    }

    // Registries
    private void setupRegistries(DynamicRegistryEvent.NewRegistry event) {
        new DynamicRegistryBuilder<>(new ResourceLocation(Epos.ID, "path"), IPath.class, this.getRegistrate().getPathSerializerRegistry())
                .setDefaultKey(new ResourceLocation(Epos.ID, "missing"))
                .create();
        new DynamicRegistryBuilder<>(new ResourceLocation(Epos.ID, "skill"), ISkill.class, this.getRegistrate().getSkillSerializerRegistry())
                .setDefaultKey(new ResourceLocation(Epos.ID, "missing"))
                .create();
        new DynamicRegistryBuilder<>(new ResourceLocation(Epos.ID, "feat"), IFeat.class, this.getRegistrate().getFeatSerializerRegistry())
                .setDefaultKey(new ResourceLocation(Epos.ID, "missing"))
                .create();
    }



    // Test Code
    @VisibleForTesting
    private void addTestCases(IEventBus modBus, IEventBus forgeBus) {
        modBus.addListener(this::testRegistryInitialization);
        modBus.addGenericListener(ISkill.class, this::testRegistryRegistration);
        modBus.addListener(this::testRegistryCodec);
    }

    @VisibleForTesting
    private void testRegistryInitialization(DynamicRegistryEvent.NewRegistry event) {
        new DynamicRegistryBuilder<>(new ResourceLocation(Epos.ID, "skill"), ISkill.class, this.getRegistrate().getSkillSerializerRegistry())
                .setDefaultKey(new ResourceLocation(Epos.ID, "missing"))
                .create();
    }

    @VisibleForTesting
    private void testRegistryRegistration(DynamicRegistryEvent.Register<ISkill, SkillSerializer> event) {
        event.getRegistry().register(new Skill(new TranslationTextComponent("missing"),
                new TranslationTextComponent("missing.desc"), 1, "0").setRegistryName(new ResourceLocation(Epos.ID, "missing")));
        event.getRegistry().register(
                new Skill(new TranslationTextComponent("test"), new TranslationTextComponent("test.desc"), 5, "1 + x")
                        .setRegistryName(new ResourceLocation(Epos.ID, "test")));
        event.getRegistry().register(new Skill(new TranslationTextComponent("test2"), new TranslationTextComponent("test2.desc"),
                10, "x * 2 / 3").setRegistryName(new ResourceLocation(Epos.ID, "test2")));
    }

    @VisibleForTesting
    private void testRegistryCodec(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            DynamicRegistry<ISkill, SkillSerializer> registry = DynamicRegistryManager.STATIC.getRegistry(new ResourceLocation(Epos.ID, "skill"));
            @Nullable
            JsonElement element = registry.toSnapshot(JsonOps.INSTANCE);
            if (element != null) {
                registry.fromSnapshot(element, JsonOps.INSTANCE);
            }
        });
    }
}
