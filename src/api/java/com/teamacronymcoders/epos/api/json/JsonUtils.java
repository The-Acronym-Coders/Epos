package com.teamacronymcoders.epos.api.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JsonUtils {
    @Nonnull
    public static String getString(JsonObject jsonObject, String fieldName) throws JsonParseException {
        return getString(jsonObject, fieldName, null);
    }

    @Nonnull
    public static String getString(JsonObject jsonObject, String fieldName, String defaultValue) throws JsonParseException {
        JsonPrimitive primitive = getPrimitive(jsonObject, fieldName);
        if (primitive != null) {
            if (primitive.isString()) {
                return primitive.getAsString();
            } else {
                throw new JsonParseException(fieldName + " is required and must be a nonnull string");
            }
        } else if (defaultValue != null) {
            return defaultValue;
        } else {
            throw new JsonParseException(fieldName + " is required and must be a nonnull string");
        }
    }

    public static int getInt(JsonObject jsonObject, String field) {
        return getInt(jsonObject, field, null);
    }

    public static int getInt(JsonObject jsonObject, String fieldName, Integer defaultValue) {
        JsonPrimitive primitive = getPrimitive(jsonObject, fieldName);
        if (primitive != null) {
            if (primitive.isNumber()) {
                return primitive.getAsInt();
            } else {
                throw new JsonParseException(fieldName + " and must be a integer");
            }
        } else if (defaultValue != null) {
            return defaultValue;
        } else {
            throw new JsonParseException(fieldName + " is required and must be a integer");
        }
    }

    public static boolean getBool(JsonObject jsonObject, String fieldName) {
        return getBool(jsonObject, fieldName, null);
    }

    public static boolean getBool(JsonObject jsonObject, String fieldName, Boolean defaultValue) {
        JsonPrimitive primitive = getPrimitive(jsonObject, fieldName);
        if (primitive != null) {
            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            } else {
                throw new JsonParseException(fieldName + " must be a boolean");
            }
        } else if (defaultValue != null) {
            return defaultValue;
        } else {
            throw new JsonParseException(fieldName + " is required and must be a boolean");
        }
    }

    @Nullable
    public static JsonPrimitive getPrimitive(JsonObject jsonObject, String fieldName) {
        if (jsonObject.has(fieldName)) {
            JsonElement jsonElement = jsonObject.get(fieldName);
            if (jsonElement.isJsonPrimitive()) {
                return jsonElement.getAsJsonPrimitive();
            }
        }
        return null;
    }

    public static ITextComponent getTranslation(JsonObject jsonObject, String fieldName, String type, ResourceLocation registryName) {
        return jsonObject.has(fieldName) ?
                TextComponent.Serializer.fromJson(jsonObject.get(fieldName)) :
                new TranslationTextComponent(type + "." + registryName.getNamespace() + "." +
                        registryName.getPath().replace("\\", ".").replace("//", ".")
                        + "." + fieldName);

    }
}
