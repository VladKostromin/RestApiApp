package com.vladkostromin.jsonserializer;

import com.google.gson.*;
import com.vladkostromin.model.Event;
import com.vladkostromin.model.User;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("name", src.getName());

        JsonArray eventsArray = new JsonArray();
        for (Event event : src.getEvents()) {
            eventsArray.add(context.serialize(event, EventSerializer.class));
        }

        jsonObject.add("events", eventsArray);
        return jsonObject;
    }
}
