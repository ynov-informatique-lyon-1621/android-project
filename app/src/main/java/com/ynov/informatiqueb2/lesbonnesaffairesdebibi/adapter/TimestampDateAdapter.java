package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

public class TimestampDateAdapter implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Long timestamp = json.getAsJsonPrimitive().getAsLong();
        Date date = new Date();
        date.setTime(timestamp);
        return date;
    }
}
