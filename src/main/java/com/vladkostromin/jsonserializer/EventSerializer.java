package com.vladkostromin.jsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.vladkostromin.model.Event;

import java.lang.reflect.Type;

public class EventSerializer implements JsonSerializer<Event> {

    @Override
    public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("userName", src.getUser().getName());
        jsonObject.addProperty("eventFileName", src.getFile().getName());
        jsonObject.addProperty("eventFilePath", src.getFile().getFilePath());
        return jsonObject;
    }
}
