package com.teamacronymcoders.epos.json.path;

//import com.google.common.collect.Lists;
//import com.hrznstudio.titanium.json.IJsonProvider;
//import com.hrznstudio.titanium.json.JsonLoader;
//import com.hrznstudio.titanium.json.jsondirector.IJsonDirector;
//import com.teamacronymcoders.epos.api.EposAPI;
//import com.teamacronymcoders.epos.api.registry.IRegistryEntry;
//import com.teamacronymcoders.epos.api.registry.RegistrationEvent;
//import com.teamacronymcoders.epos.api.registry.Registry;
//import com.teamacronymcoders.epos.json.JsonLoader;
//import net.minecraft.resources.IResource;
//import net.minecraft.resources.IResourceManager;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.resource.IResourceType;
//import net.minecraftforge.resource.ISelectiveResourceReloadListener;
//import org.apache.commons.io.IOUtils;
//import org.apache.logging.log4j.Logger;
//
//import javax.annotation.Nullable;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
//public class PathJsonLoader<T extends IRegistryEntry> extends JsonLoader<T> implements ISelectiveResourceReloadListener {
//    private final String type;
//    private final IResourceType resourceType;
//    private final Class<T> tClass;
//    private final Registry<T> registry;
//
//    public PathJsonLoader(IResourceType resourceType, Class<T> tClass, Registry<T> registry, IJsonDirector<T> director, IJsonProvider<T> jsonProvider) {
//        super("path", EposAPI.LOGGER, director, jsonProvider);
//        this.type = "path";
//        this.resourceType = resourceType;
//        this.tClass = tClass;
//        this.registry = registry;
//    }
//
//    @Override
//    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
//        if (resourcePredicate.test(this.getResourceType())) {
//            registry.clear();
//            RegistrationEvent<T> registryEvent = new RegistrationEvent<>(tClass, registry);
//            List<T> pathList = new ArrayList<>();
//            registryEvent.register(loadValues(resourceManager));
//            MinecraftForge.EVENT_BUS.post(registryEvent);
//        }
//    }
//
//    private List<T> loadValues(IResourceManager resourceManager) {
//        final String folder = "epos/" + type;
//        final String extension = ".json";
//        return resourceManager.getAllResourceLocations(folder, n -> n.endsWith(extension))
//            .stream()
//            .map(resource -> new ResourceLocation(resource.getNamespace(), resource.getPath().substring(folder.length() + 1, resource.getPath().length() - extension.length())))
//            .map(resource -> {
//                try {
//                    return resourceManager.getAllResources(resource);
//                } catch (IOException exception) {
//                    EposAPI.LOGGER.warn("Failed to Load Files for " + type, exception);
//                    return Lists.<IResource>newArrayList();
//                }
//            })
//            .map(resources -> resources.stream()
//                .map(resource -> {
//                    try {
//                        return gson.fromJson(IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8), tClass);
//                    } catch (IOException e) {
//                        EposAPI.LOGGER.warn("Failed to Parse " + type + " for file: " + resource.getLocation().toString());
//                        return null;
//                    } finally {
//                        IOUtils.closeQuietly(resource);
//                    }
//                    //TODO Proper Merging
//                }).reduce((first, last) -> last == null ? first : last)
//            )
//            .filter(Optional::isPresent)
//            .map(Optional::get)
//            .collect(Collectors.toList());
//    }
//
//    @Nullable
//    @Override
//    public IResourceType getResourceType() {
//        return resourceType;
//    }
//}