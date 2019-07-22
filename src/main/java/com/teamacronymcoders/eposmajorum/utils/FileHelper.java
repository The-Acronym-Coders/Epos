package com.teamacronymcoders.eposmajorum.utils;

import com.teamacronymcoders.eposmajorum.EposMajorum;
import com.teamacronymcoders.eposmajorum.api.EposAPI;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelper {
    public static File root = new File(FMLPaths.CONFIGDIR.get().toFile(), EposAPI.ID);

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
            EposMajorum.LOGGER.error("Couldn't create File: " + file.getName());
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
