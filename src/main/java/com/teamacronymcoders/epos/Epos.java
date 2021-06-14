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
import com.mojang.serialization.JsonOps;
import com.teamacronymcoders.epos.api.registry.DynamicRegistry;
import com.teamacronymcoders.epos.api.registry.DynamicRegistryBuilder;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.SkillSerializer;
import com.teamacronymcoders.epos.client.EposClientHandler;
import com.teamacronymcoders.epos.network.DynamicRegistryPacket;
import com.teamacronymcoders.epos.registry.DynamicRegistryHandler;
import com.teamacronymcoders.epos.registry.DynamicRegistryListener;
import com.teamacronymcoders.epos.registry.EposRegistrate;
import com.teamacronymcoders.epos.registry.SkillRegistrar;
import com.teamacronymcoders.epos.skill.Skill;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.logging.Logger;


@Mod(Epos.ID)
public class Epos {

    public static final String ID = "epos";
    private static Epos instance;
    private final EposRegistrate registrate;
    private final DynamicRegistryListener registryListener;
    private SimpleChannel network;
    private static final Logger LOGGER = Logger.getLogger(ID);

    public Epos() {
        instance = this;
        this.registrate = EposRegistrate.create(ID);
        this.registryListener = new DynamicRegistryListener();
        SkillRegistrar.register();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus(), forgeBus = MinecraftForge.EVENT_BUS;
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new EposClientHandler(modBus, forgeBus));
        modBus.addListener(this::setup);
        forgeBus.addListener(this::addReloadListeners);
        forgeBus.addListener(this::serverTick);
        // Uncomment for test cases to run
        // this.addTestCases(modBus, forgeBus);
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

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            this.network = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Epos.ID, "network"))
                    .clientAcceptedVersions(str -> true).serverAcceptedVersions(str -> true)
                    .networkProtocolVersion(() -> Epos.ID + ":1").simpleChannel();

            this.network.messageBuilder(DynamicRegistryPacket.class, 0).encoder(DynamicRegistryPacket::encode)
                    .decoder(DynamicRegistryPacket::new).consumer(DynamicRegistryPacket::handle).add();

            DynamicRegistryBuilder<ISkill, SkillSerializer> builder = new DynamicRegistryBuilder<>();
            builder.setName(new ResourceLocation(Epos.ID, "skill")).setType(ISkill.class)
                    .setSerializer(this.getRegistrate().getSkillSerializerRegistry())
                    .setMissingEntry(() -> new Skill(new TranslationTextComponent("skill.epos.missing"),
                            new TranslationTextComponent("skill.epos.missing.desc"), 1, "0"))
                    .create();
        });
    }

    private void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(this.registryListener);
    }

    private void serverTick(ServerTickEvent event) {
        if (DynamicRegistryHandler.INSTANCE.needsSync()) {
            DynamicRegistryHandler.INSTANCE.synced();
            this.getNetwork().send(PacketDistributor.ALL.noArg(), DynamicRegistryHandler.INSTANCE.syncPacket());
        }
    }

    @VisibleForTesting
    private void addTestCases(IEventBus modBus, IEventBus forgeBus) {
        modBus.addListener(this::testRegistrySerialization);
    }

    @VisibleForTesting
    private void testRegistrySerialization(FMLCommonSetupEvent event) {
        DynamicRegistryBuilder<ISkill, SkillSerializer> builder = new DynamicRegistryBuilder<>();
        builder.setName(new ResourceLocation(Epos.ID, "skill")).setType(ISkill.class)
                .setSerializer(this.getRegistrate().getSkillSerializerRegistry())
                .setMissingEntry(() -> new Skill(new TranslationTextComponent("missing"),
                        new TranslationTextComponent("missing.desc"), 1, "0"));
        DynamicRegistry<ISkill, SkillSerializer> registry = new DynamicRegistry<>(builder);
        registry.register(
                new Skill(new TranslationTextComponent("test"), new TranslationTextComponent("test.desc"), 5, "1 + x")
                        .setRegistryName(new ResourceLocation(Epos.ID, "test")));
        registry.register(new Skill(new TranslationTextComponent("test2"), new TranslationTextComponent("test2.desc"),
                10, "x * 2 / 3").setRegistryName(new ResourceLocation(Epos.ID, "test2")));
        registry.freeze();
        registry.encodeRegistry(JsonOps.INSTANCE).result().ifPresent(element -> {
            System.out.println(element);
            registry.updateRegistry(JsonOps.INSTANCE, element);
        });
    }
}
