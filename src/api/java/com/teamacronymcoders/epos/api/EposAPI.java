package com.teamacronymcoders.epos.api;

import com.teamacronymcoders.epos.api.skill.ISkill;
import net.minecraftforge.registries.ForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EposAPI {

    public static final String ID = "epos";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final Supplier<ForgeRegistry<ISkill>> SKILL_REGISTRY;

    public EposAPI() {
        //this.SKILL_REGISTRY = new RegistryBuilder<ISkill>()
    }

}
