package com.teamacronymcoders.eposmajorum.utils;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;

public class FileUtil {
    public static File root = new File(FMLPaths.CONFIGDIR.get().toFile(), EposAPI.ID);

    public static File getConfigFile(String config) {
        if (!root.exists()) {
            root.mkdir();
        }
        return new File(root, config + ".toml");
    }
}
