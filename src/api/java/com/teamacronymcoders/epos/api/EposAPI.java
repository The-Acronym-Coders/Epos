package com.teamacronymcoders.epos.api;

import com.teamacronymcoders.epos.api.locks.ILockRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EposAPI {

    public static final String ID = "epos";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    //public static final Supplier<ForgeRegistry<ISkill>> SKILL_REGISTRY;
    private static ILockRegistry LOCK_REGISTRY;

    public EposAPI() {
        //this.SKILL_REGISTRY = new RegistryBuilder<ISkill>()
    }

    //TODO: JavaDocs
    public static ILockRegistry getLockRegistry() {
        //Harmless race
        if (LOCK_REGISTRY == null) {
            try {
                Class<?> clazz = Class.forName("com.teamacronymcoders.epos.locks.LockRegistry");
                LOCK_REGISTRY = (ILockRegistry) clazz.getField("INSTANCE").get(null);
            } catch (ReflectiveOperationException ex) {
                LOGGER.fatal("Error retrieving Lock Registry, Epos may be absent, damaged, or outdated.");
            }
        }
        return LOCK_REGISTRY;
    }
}
