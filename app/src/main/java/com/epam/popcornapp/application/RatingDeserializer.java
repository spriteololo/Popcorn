package com.epam.popcornapp.application;

import com.epam.popcornapp.pojo.base.rating.Rating;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class RatingDeserializer implements JsonDeserializer<Rating> {

    @Override
    public Rating deserialize(final JsonElement json, final Type typeOfT,
                              final JsonDeserializationContext context) throws JsonParseException {

        final Gson gson = new Gson();

        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonElement rated = jsonObject.get("rated");

        if (rated.isJsonPrimitive()) {
            jsonObject.remove("rated");

            final JsonObject object = new JsonObject();
            object.addProperty("value", "0.0");
            jsonObject.add("rated", object);
        }

        return gson.fromJson(json, Rating.class);
    }
}
