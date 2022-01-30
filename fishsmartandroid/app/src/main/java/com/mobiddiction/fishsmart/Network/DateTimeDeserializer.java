package com.mobiddiction.fishsmart.Network;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 16/08/2014.
 */
public class DateTimeDeserializer implements JsonSerializer<Date>, JsonDeserializer<Date>
{
    private static final SimpleDateFormat JSON_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ");
    private static final SimpleDateFormat JSON_DATE_TIME2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        try
        {
            return JSON_DATE_TIME.parse(json.getAsJsonPrimitive().getAsString());
        }
        catch (ParseException pe)
        {
            try {
                return JSON_DATE_TIME2.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ParseException pe2) {
                throw new JsonParseException(pe2);
            }
        }
    }

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext)
    {
        return new JsonPrimitive(JSON_DATE_TIME.format(date));
    }
}
