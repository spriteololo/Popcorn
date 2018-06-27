package com.epam.popcornapp.application;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(final JsonElement element,
                            final Type arg1,
                            final JsonDeserializationContext arg2)
            throws JsonParseException {
        final String date = element.getAsString();

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            return formatter.parse(date);
        } catch (final ParseException e) {
            System.out.println("Failed to parse Date due to:" + e);
            return null;
        }
    }
}