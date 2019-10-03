package com.teamacronymcoders.epos.api;

import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.MissingPath;
import com.teamacronymcoders.epos.api.pathfeature.PathFeatureProvider;
import com.teamacronymcoders.epos.api.registry.Registry;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.MissingSkill;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class EposAPI {
    public static final String ID = "epos";

    public static final Registry<IPath> PATH_REGISTRY = new Registry<>(MissingPath::new);
    public static final Registry<ISkill> SKILL_REGISTRY = new Registry<>(MissingSkill::new);

    public static final IForgeRegistry<Feat> FEAT_REGISTRY =
            RegistryManager.ACTIVE.getRegistry(Feat.class);
    public static final IForgeRegistry<PathFeatureProvider> PATH_FEATURE_PROVIDER_REGISTRY =
            RegistryManager.ACTIVE.getRegistry(PathFeatureProvider.class);
}
