package com.teamacronymcoders.eposmajorum.api.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

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
            if (primitive.isString()) {
                return primitive.getAsInt();
            } else {
                throw new JsonParseException(fieldName + " is required and must be a integer");
            }
        } else if (defaultValue != null) {
            return defaultValue;
        } else {
            throw new JsonParseException(fieldName + " is required and must be a integer");
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
}
