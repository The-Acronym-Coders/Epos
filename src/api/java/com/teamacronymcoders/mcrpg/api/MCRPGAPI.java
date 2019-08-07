package com.teamacronymcoders.mcrpg.api;

import com.teamacronymcoders.mcrpg.api.feat.IFeat;
import com.teamacronymcoders.mcrpg.api.feat.MissingFeat;
import com.teamacronymcoders.mcrpg.api.path.IPath;
import com.teamacronymcoders.mcrpg.api.path.MissingPath;
import com.teamacronymcoders.mcrpg.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.mcrpg.api.pathfeature.MissingPathFeatureProvider;
import com.teamacronymcoders.mcrpg.api.registry.Registry;
import com.teamacronymcoders.mcrpg.api.skill.ISkill;
import com.teamacronymcoders.mcrpg.api.skill.MissingSkill;

public class MCRPGAPI {
    public static final String ID = "mcrpg";

    public static final Registry<IPath> PATH_REGISTRY = new Registry<>(MissingPath::new);
    public static final Registry<IFeat> FEAT_REGISTRY = new Registry<>(MissingFeat::new);
    public static final Registry<ISkill> SKILL_REGISTRY = new Registry<>(MissingSkill::new);
    public static final Registry<IPathFeatureProvider> PATH_FEATURE_PROVIDER_REGISTRY =
            new Registry<>(MissingPathFeatureProvider::new);
}
