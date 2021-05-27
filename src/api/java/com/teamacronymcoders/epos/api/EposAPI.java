package com.teamacronymcoders.epos.api;

import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.feat.MissingFeat;
import com.teamacronymcoders.epos.api.path.IClass;
import com.teamacronymcoders.epos.api.path.MissingClass;
import com.teamacronymcoders.epos.api.path.feature.CharacterClassFeatureProvider;
import com.teamacronymcoders.epos.api.registry.Registry;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.MissingSkill;
import com.teamacronymcoders.epos.api.source.SourceType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EposAPI {

    static {
        init();
    }

    public static final String ID = "epos";
    public static final Logger LOGGER = LogManager.getLogger(ID);
    //public static IForgeRegistry<Path> PATH = RegistryManager.ACTIVE.getRegistry(Path.class);
    //public static IForgeRegistry<Skill> SKILL = RegistryManager.ACTIVE.getRegistry(Skill.class);
    //public static IForgeRegistry<Feat> FEAT = RegistryManager.ACTIVE.getRegistry(Feat.class);

    public static final Registry<IClass> CLASS_REGISTRY = new Registry<>(MissingClass::new);
    public static final Registry<IFeat> FEAT_REGISTRY = new Registry<>(MissingFeat::new);
    public static final Registry<ISkill> SKILL_REGISTRY = new Registry<>(MissingSkill::new);

    public static final IForgeRegistry<CharacterClassFeatureProvider> CLASS_FEATURE_PROVIDERS = RegistryManager.ACTIVE.getRegistry(CharacterClassFeatureProvider.class);
    public static final IForgeRegistry<SourceType> SOURCE_TYPES = RegistryManager.ACTIVE.getRegistry(SourceType.class);

    @SuppressWarnings("unchecked")
    private static void init() {
        //makeRegistry("path", Path.class).create();
        //makeRegistry("skill", Skill.class).create();
        //makeRegistry("feat", Feat.class).create();
        makeRegistry("source", SourceType.class).create();
    }

    public static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(String name, Class<T> type) {
        return new RegistryBuilder<T>()
            .setName(new ResourceLocation(ID, name))
            .setIDRange(1, Integer.MAX_VALUE - 1)
            .disableSaving()
            .setType(type);
    }

}
