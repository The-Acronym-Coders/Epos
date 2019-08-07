package com.teamacronymcoders.mcrpg.utils;

import com.teamacronymcoders.mcrpg.MCRPG;
import com.teamacronymcoders.mcrpg.api.MCRPGAPI;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.IOException;

public class FileHelper {
    public static File root = new File(FMLPaths.CONFIGDIR.get().toFile(), MCRPGAPI.ID);

    public static File getOrGenFile(String file) {
        return createFile(new File(file));
    }

    public static File getOrGenFile(String file, String path) {
        return createFile(new File(path, file));
    }

    private static File createFile(File file) {
        if (file.exists()) {
            return file;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            MCRPG.LOGGER.error("Couldn't create File: " + file.getName());
        }

        return file;
    }

    public static File getConfigFile(String config) {
        if (!root.exists()) {
            root.mkdir();
        }
        return new File(root,config + ".toml");
    }
}
