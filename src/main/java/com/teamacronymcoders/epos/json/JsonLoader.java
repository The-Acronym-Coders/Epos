package com.teamacronymcoders.epos.json;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.registry.IRegistryEntry;
import com.teamacronymcoders.epos.api.registry.RegistrationEvent;
import com.teamacronymcoders.epos.api.registry.Registry;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public class JsonLoader<T extends IRegistryEntry> implements ISelectiveResourceReloadListener {
    private final IResourceType resourceType;
    private final Class<T> tClass;
    private final Registry<T> registry;
    private final String type;

    private final Gson gson;

    public JsonLoader(String type, IResourceType resourceType, Class<T> tClass, Registry<T> registry) {
        this.type = type;
        this.resourceType = resourceType;
        this.tClass = tClass;
        this.registry = registry;
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        if (resourcePredicate.test(this.getResourceType())) {
            registry.clear();

            RegistrationEvent<T> registryEvent = new RegistrationEvent<>(tClass, registry);

            registryEvent.register(loadValues(resourceManager));

            MinecraftForge.EVENT_BUS.post(registryEvent);
        }
    }

    private List<T> loadValues(IResourceManager resourceManager) {
        final String folder = "epos/" + type;
        final String extension = ".json";
        return resourceManager.getAllResourceLocations(folder, n -> n.endsWith(extension))
                .stream()
                .map(resource -> new ResourceLocation(resource.getNamespace(),
                        resource.getPath().substring(folder.length() + 1, resource.getPath().length() - extension.length())))
                .map(resource -> {
                    try {
                        return resourceManager.getAllResources(resource);
                    } catch (IOException exception) {
                        Epos.LOGGER.warn("Failed to Load Files for " + type, exception);
                        return Lists.<IResource>newArrayList();
                    }
                })
                .map(resources -> resources.stream()
                        .map(resource -> {
                            try {
                                return gson.fromJson(IOUtils.toString(resource.getInputStream(),
                                        StandardCharsets.UTF_8), tClass);
                            } catch (IOException e) {
                                Epos.LOGGER.warn("Failed to Parse " + type + " for file: "
                                        + resource.getLocation().toString());
                                return null;
                            } finally {
                                IOUtils.closeQuietly(resource);
                            }
                            //TODO Proper Merging
                        }).reduce((first, last) -> last == null ? first : last)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public IResourceType getResourceType() {
        return resourceType;
    }
}
