package com.ynov.informatiqueb2.lesbonnesaffairesdebibi.Adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

public class AdapterCurrentDate implements JsonDeserializer {
    Date currentDate;
    Long currentDateAsLong;
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        currentDateAsLong = json.getAsJsonPrimitive().getAsLong();
        currentDate = new Date();
        currentDate.setTime(currentDateAsLong);

        return currentDate;
    }
}
