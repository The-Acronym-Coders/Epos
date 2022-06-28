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
import com.teamacronymcoders.epos.api.capability.EposCapabilities;
import com.teamacronymcoders.epos.api.character.CharacterSheet;
import com.teamacronymcoders.epos.api.character.capability.CharacterSheetCapabilityProvider;
import com.teamacronymcoders.epos.client.EposClientHandler;
import com.teamacronymcoders.epos.impl.EposFeats;
import com.teamacronymcoders.epos.registry.*;
import com.teamacronymcoders.epos.util.EposRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;


@Mod(Epos.ID)
public class Epos {

    @VisibleForTesting
    public static final boolean IS_TESTING = false;

    public static final String ID = "epos";
    private static Epos instance;
    private final EposRegistrate registrate;
    private SimpleChannel network;
    private static final Logger LOGGER = LogManager.getLogger(ID);
    private EposRegistries registries;

    public Epos() {
        instance = this;
        this.registrate = EposRegistrate.create(ID);
        PathFeatureRegistrar.register();
        PathRegistrar.register();
        SkillRegistrar.register();
        FeatRegistrar.register();
        EposFeats.registerEventManagers();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus(), forgeBus = MinecraftForge.EVENT_BUS;
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new EposClientHandler(modBus, forgeBus));
        modBus.addListener(this::setup);
        modBus.addListener(this::registerCapabilities);

        // Caps
        forgeBus.addGenericListener(Entity.class, this::setupCharacterSheetCaps);
        forgeBus.addListener(this::respawnCharacterSheetCaps);

        // Uncomment for test cases to run
        if (IS_TESTING) {
            // TODO: Setup new Tests
            //this.addTestCases(modBus, forgeBus);
        } else {

//            modBus.addListener(this::setupRegistries);
//            modBus.addGenericListener(IPath.class, this::registerPaths);
//            modBus.addGenericListener(ISkill.class, this::registerSkills);
//            modBus.addGenericListener(IFeat.class, this::registerFeats);
            modBus.addListener(this::generateRegistryJson);
        }

        for (int i = 0; i < 10; i++) {
            getLogger().info(UUID.randomUUID());
        }
    }

    public static Epos instance() {
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

    public EposRegistries getRegistries() {
        this.registries = new EposRegistries();
        return this.registries;
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
            this.network = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Epos.ID, "network"))
                    .clientAcceptedVersions(str -> true).serverAcceptedVersions(str -> true)
                    .networkProtocolVersion(() -> Epos.ID + ":1").simpleChannel();
        });
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(CharacterSheet.class);
    }

    // Registries

    //TODO: Look into moving this over to deferred register

//    private void registerPaths(DynamicRegistryEvent.Register<IPath, PathSerializer> event) {
//        new EposPaths();
//        event.getRegistry().registerAll(PathBuilder.BUILT_PATHS.toArray(new IPath[0]));
//    }
//
//    private void registerSkills(DynamicRegistryEvent.Register<ISkill, SkillSerializer> event) {
//        new EposSkills();
//        event.getRegistry().registerAll(SkillBuilder.BUILT_SKILLS.toArray(new ISkill[0]));
//    }
//
//    private void registerFeats(DynamicRegistryEvent.Register<IFeat, FeatSerializer> event) {
//        event.getRegistry().registerAll(FeatBuilder.BUILT_FEATS.toArray(new IFeat[0]));
//    }

    private void generateRegistryJson(FMLLoadCompleteEvent event) {
//        event.enqueueWork(() -> {
//            DynamicRegistry<IPath, PathSerializer> pathRegistry = DynamicRegistryManager.STATIC.getRegistry(new ResourceLocation(Epos.ID, "path"));
//            DynamicRegistry<ISkill, SkillSerializer> skillRegistry = DynamicRegistryManager.STATIC.getRegistry(new ResourceLocation(Epos.ID, "skill"));
//            DynamicRegistry<IFeat, FeatSerializer> featRegistry = DynamicRegistryManager.STATIC.getRegistry(new ResourceLocation(Epos.ID, "feat"));
//            @Nullable
//            JsonElement pathElement = pathRegistry.toSnapshot(JsonOps.INSTANCE);
//            @Nullable
//            JsonElement skillElement = skillRegistry.toSnapshot(JsonOps.INSTANCE);
//            @Nullable
//            JsonElement featElement = featRegistry.toSnapshot(JsonOps.INSTANCE);
//            if (pathElement != null) {
//                pathRegistry.fromSnapshot(pathElement, JsonOps.INSTANCE, false);
//            }
//            if (skillElement != null) {
//                skillRegistry.fromSnapshot(skillElement, JsonOps.INSTANCE, false);
//            }
//            if (featElement != null) {
//                featRegistry.fromSnapshot(featElement, JsonOps.INSTANCE, false);
//            }
//        });
    }

    // Test Code

}
