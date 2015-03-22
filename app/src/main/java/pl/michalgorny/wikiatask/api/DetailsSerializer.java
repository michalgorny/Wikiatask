package pl.michalgorny.wikiatask.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.michalgorny.wikiatask.api.responses.GetItemDetailsResponse;
import pl.michalgorny.wikiatask.pojos.Wiki;


/**
 * Custom serializer handling response from request getDetails.
 * It filters only information which is needed to List of @Wiki objects.
 */
public class DetailsSerializer implements JsonDeserializer<GetItemDetailsResponse>{
    @Override
    public GetItemDetailsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Wiki> wikis = new ArrayList<>();

        Set<Map.Entry<String, JsonElement>> entrySet = json.getAsJsonObject().get("items").getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            JsonObject object = entry.getValue().getAsJsonObject();

            Wiki.WikiBuilder wikiBuilder = Wiki.builder();

            wikiBuilder
                    .id(object.get("id").getAsInt())
                    .title(object.get("title").getAsString())
                    .url(object.get("url").getAsString())
                    .urlImage(object.get("image").getAsString());

            wikis.add(wikiBuilder.build());
        }

        final GetItemDetailsResponse.GetItemDetailsResponseBuilder builder = GetItemDetailsResponse.builder();

        return builder
                .wikiList(wikis)
                .build();
    }
}
