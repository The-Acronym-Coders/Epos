package com.teamacronymcoders.epos.api;

import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.feat.MissingFeat;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.MissingPath;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.epos.api.pathfeature.MissingPathFeatureProvider;
import com.teamacronymcoders.epos.api.registry.Registry;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.MissingSkill;

public class EposAPI {
    public static final String ID = "epos";

    public static final Registry<IPath> PATH_REGISTRY = new Registry<>(MissingPath::new);
    public static final Registry<IFeat> FEAT_REGISTRY = new Registry<>(MissingFeat::new);
    public static final Registry<ISkill> SKILL_REGISTRY = new Registry<>(MissingSkill::new);
    public static final Registry<IPathFeatureProvider> PATH_FEATURE_PROVIDER_REGISTRY =
            new Registry<>(MissingPathFeatureProvider::new);
}
