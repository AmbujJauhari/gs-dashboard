package com.ambuj.service;

import com.gigaspaces.document.SpaceDocument;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Aj on 02-06-2016.
 */
public class SpaceDocumentToJSON implements JsonSerializer<SpaceDocument> {
    @Override
    public JsonElement serialize(SpaceDocument spaceDocument, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson gson = new Gson();

        return new JsonPrimitive(spaceDocument.getProperties().toString());
    }
}
