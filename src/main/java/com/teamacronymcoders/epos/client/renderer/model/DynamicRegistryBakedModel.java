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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.teamacronymcoders.epos.Epos;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.resource.IResourceType;

public class DynamicRegistryBakedModel implements IBakedModel {

    public static final ResourceLocation MISSING_MODEL_LOCATION = new ResourceLocation(Epos.ID, "general/missing");
    private IBakedModel missingModel;
    private final boolean usesBlockLight;
    private final ItemCameraTransforms transforms;
    private final ItemOverrideList overrides;
    private final Map<ResourceLocation, IBakedModel> models;

    public DynamicRegistryBakedModel(boolean usesBlockLight, ItemCameraTransforms transforms,
            Map<ResourceLocation, IBakedModel> models, ItemOverrideList overrides) {
        this.models = models;
        this.missingModel = this.models.get(MISSING_MODEL_LOCATION);
        this.usesBlockLight = usesBlockLight;
        this.transforms = transforms;
        this.overrides = overrides;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.missingModel.getParticleTexture(EmptyModelData.INSTANCE);
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
    public TextureAtlasSprite getParticleTexture(IModelData data) {
        return Optional.ofNullable(data.getData(EposModelData.LOCATION_PROPERTY)).map(this.models::get)
                .map(model -> model.getParticleTexture(EmptyModelData.INSTANCE)).orElse(this.getParticleIcon());
    }

    @Override
    public ItemCameraTransforms getTransforms() {
        return this.transforms;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.overrides;
    }

    public static class Unbaked implements IModelGeometry<Unbaked> {

        private final EposResourceType type;
        private final Map<ResourceLocation, IUnbakedModel> models;

        public Unbaked(EposResourceType type) {
            this.type = type;
            this.models = new HashMap<>();
        }

        @Override
        public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery,
                Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform,
                ItemOverrideList overrides, ResourceLocation modelLocation) {
            Map<ResourceLocation, IBakedModel> bakedModels = Maps.transformEntries(this.models,
                    (loc, model) -> model.bake(bakery, spriteGetter, modelTransform, loc));
            return new DynamicRegistryBakedModel(owner.isSideLit(), owner.getCameraTransforms(), bakedModels,
                    overrides);
        }

        @Override
        public Collection<RenderMaterial> getTextures(IModelConfiguration owner,
                Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            Set<RenderMaterial> materials = new HashSet<>();

            IUnbakedModel vanillaMissingModel = ModelLoader.instance()
                    .getModelOrMissing(ModelBakery.MISSING_MODEL_LOCATION);

            IUnbakedModel missingModel = this.models.computeIfAbsent(MISSING_MODEL_LOCATION, loc -> {
                IUnbakedModel model = modelGetter.apply(loc);
                materials.addAll(model.getMaterials(modelGetter, missingTextureErrors));
                return model;
            });

            this.type.getModels().forEach(modelLoc -> this.models.computeIfAbsent(modelLoc, loc -> {
                IUnbakedModel model = modelGetter.apply(loc);
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
        public void onResourceManagerReload(IResourceManager resourceManager) {
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
