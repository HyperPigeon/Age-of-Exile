package com.robertx22.age_of_exile.aoe_data.datapacks;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import com.robertx22.age_of_exile.database.data.StatModifier;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtils {

    public static StringWriter stringWriter;
    public static JsonWriter jsonWriter;

    static {
        stringWriter = new StringWriter();
        jsonWriter = new JsonWriter(stringWriter);
        jsonWriter.setIndent("");
        jsonWriter.setLenient(true);
    }

    public static void addStats(List<StatModifier> mods, JsonObject json, String id) {
        JsonArray array = new JsonArray();
        mods.stream()
            .map(x -> x.toJson())
            .collect(Collectors.toList())
            .forEach(x -> array.add(x));
        json.add(id, array);
    }

    public static List<StatModifier> getStats(JsonObject json, String id) {
        List<StatModifier> mods = new ArrayList<>();
        json.getAsJsonArray(id)
            .forEach(x -> mods.add(StatModifier.EMPTY.fromJson(x.getAsJsonObject())));
        return mods;
    }

    public static JsonArray stringListToJsonArray(List<String> list) {
        JsonArray json = new JsonArray();
        list.forEach(x -> json.add(new JsonPrimitive(x)));
        return json;
    }

    public static List<String> jsonArrayToStringList(JsonArray json) {
        List<String> list = new ArrayList<>();
        json.forEach(x -> list.add(x.getAsString()));
        return list;
    }

}
