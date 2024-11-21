/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import pos.bind.DataTypeAdapterFactory;

/**
 *
 * @author husainazkas
 */
public final class JsonParser {

    private static Gson gson;

    private static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .serializeNulls()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapterFactory(new DataTypeAdapterFactory())
                    .create();
        }
        return gson;
    }

    static public <T> T fromJson(String json, Class<T> c) throws JsonSyntaxException {
        return getGson().fromJson(json, c);
    }

    static public <T> T fromJson(String json, Type type) throws JsonSyntaxException {
        return getGson().fromJson(json, type);
    }

    static public String toJson(Object data) {
        return getGson().toJson(data);
    }
}
