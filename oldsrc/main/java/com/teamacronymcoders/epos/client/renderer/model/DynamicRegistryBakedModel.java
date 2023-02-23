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

package com.teamacronymcoders.epos.client.renderer.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.teamacronymcoders.epos.Epos;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.resource.IResourceType;

import java.util.*;
import java.util.function.Function;

public class DynamicRegistryBakedModel implements BakedModel {

    public static final ResourceLocation MISSING_MODEL_LOCATION = new ResourceLocation(Epos.ID, "general/missing");
    private BakedModel missingModel;
    private final boolean usesBlockLight;
    private final ItemTransforms transforms;
    private final ItemOverrides overrides;
    private final Map<ResourceLocation, BakedModel> models;

    public DynamicRegistryBakedModel(boolean usesBlockLight, ItemTransforms transforms,
                                     Map<ResourceLocation, BakedModel> models, ItemOverrides overrides) {
        this.models = models;
        this.missingModel = this.models.get(MISSING_MODEL_LOCATION);
        this.usesBlockLight = usesBlockLight;
        this.transforms = transforms;
        this.overrides = overrides;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.missingModel.getParticleIcon(EmptyModelData.INSTANCE);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
        return this.missingModel.getQuads(state, side, rand, EmptyModelData.INSTANCE);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
        return Optional.ofNullable(extraData.getData(EposModelData.LOCATION_PROPERTY)).map(this.models::get)
                .map(model -> model.getQuads(state, side, rand, EmptyModelData.INSTANCE))
                .orElse(this.getQuads(state, side, rand));
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return this.usesBlockLight;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon(IModelData data) {
        return Optional.ofNullable(data.getData(EposModelData.LOCATION_PROPERTY)).map(this.models::get)
                .map(model -> model.getParticleIcon(EmptyModelData.INSTANCE)).orElse(this.getParticleIcon());
    }

    @Override
    public ItemTransforms getTransforms() {
        return this.transforms;
    }

    @Override
    public ItemOverrides getOverrides() {
        return this.overrides;
    }

    public static class Unbaked implements IModelGeometry<Unbaked> {

        private final EposResourceType type;
        private final Map<ResourceLocation, UnbakedModel> models;

        public Unbaked(EposResourceType type) {
            this.type = type;
            this.models = new HashMap<>();
        }

        @Override
        public BakedModel bake(IModelConfiguration owner, ModelBakery bakery,
                               Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform,
                               ItemOverrides overrides, ResourceLocation modelLocation) {
            Map<ResourceLocation, BakedModel> bakedModels = Maps.transformEntries(this.models,
                    (loc, model) -> model.bake(bakery, spriteGetter, modelTransform, loc));
            return new DynamicRegistryBakedModel(owner.isSideLit(), owner.getCameraTransforms(), bakedModels,
                    overrides);
        }

        @Override
        public Collection<Material> getTextures(IModelConfiguration owner,
                                                      Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            Set<Material> materials = new HashSet<>();

            UnbakedModel vanillaMissingModel = ModelLoader.instance()
                    .getModelOrMissing(ModelBakery.MISSING_MODEL_LOCATION);

            UnbakedModel missingModel = this.models.computeIfAbsent(MISSING_MODEL_LOCATION, loc -> {
                UnbakedModel model = modelGetter.apply(loc);
                materials.addAll(model.getMaterials(modelGetter, missingTextureErrors));
                return model;
            });

            this.type.getModels().forEach(modelLoc -> this.models.computeIfAbsent(modelLoc, loc -> {
                UnbakedModel model = modelGetter.apply(loc);
                if (model == vanillaMissingModel)
                    model = missingModel;
                materials.addAll(model.getMaterials(modelGetter, missingTextureErrors));
                return model;
            }));

            return materials;
        }

    }

    public static class Loader implements IModelLoader<Unbaked> {

        private final EposResourceType type;

        public Loader(EposResourceType type) {
            this.type = type;
        }

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) {
        }

        @Override
        public IResourceType getResourceType() {
            return this.type;
        }

        @Override
        public Unbaked read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
            return new Unbaked(this.type);
        }
    }
}
