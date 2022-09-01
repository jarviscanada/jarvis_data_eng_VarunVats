package ca.jrvs.apps.twitter.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;

public class JsonUtils {
    public static Tweet getTweetFromJson(String jsonStr) {
        // Convert String into JSON Object and then create a tweet from it
        JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
        String createdAt = jsonObject.get("created_at").getAsString();
        long id = jsonObject.get("id").getAsLong();
        String text = jsonObject.get("text").getAsString();
        int retweetCount = jsonObject.get("retweet_count").getAsInt();
        int favouriteCount = jsonObject.get("favorite_count").getAsInt();
        boolean favourited = jsonObject.get("favorited").getAsBoolean();
        boolean retweeted = jsonObject.get("retweeted").getAsBoolean();

        // Construct entities object from JSON
        JsonObject jEntities = jsonObject.get("entities").getAsJsonObject();
        JsonArray jHashTags = jEntities.get("hashtags").getAsJsonArray();
        JsonArray jUserMentions = jEntities.get("user_mentions").getAsJsonArray();

        // Construct hashtags object from JSON
        ArrayList<HashTag> hashTags = new ArrayList<>();
        Iterator<JsonElement> hashIter = jHashTags.iterator();
        JsonObject tag;
        ArrayList<Integer> hashIndices;
        Iterator<JsonElement> hashIndIter;
        String hashText;
        while (hashIter.hasNext()) {
            tag = hashIter.next().getAsJsonObject();
            hashIndices = new ArrayList<>();
            hashIndIter = tag.get("indices").getAsJsonArray().iterator();
            while (hashIndIter.hasNext()) {
                hashIndices.add(new Integer(hashIndIter.next().getAsInt()));
            }
            hashText = tag.get("text").getAsString();
            hashTags.add(new HashTag(hashIndices, hashText));
        }

        // Construct usermentions object from JSON
        ArrayList<UserMention> userMentions = new ArrayList<>();
        Iterator<JsonElement> userIter = jUserMentions.iterator();
        JsonObject user;
        long userMentionId;
        ArrayList<Integer> userIndices;
        Iterator<JsonElement> userIndIter;
        String name, screenName;
        while (userIter.hasNext()) {
            user = userIter.next().getAsJsonObject();
            userMentionId = user.get("id").getAsLong();
            userIndices = new ArrayList<>();
            userIndIter = user.get("indices").getAsJsonArray().iterator();
            while (userIndIter.hasNext()) {
                userIndices.add(new Integer(userIndIter.next().getAsInt()));
            }
            name = user.get("name").getAsString();
            screenName = user.get("screen_name").getAsString();
            userMentions.add(new UserMention(userMentionId, userIndices, name, screenName));
        }

        Entities entities = new Entities(hashTags, userMentions);

        // Construct coordinates object from JSON
        JsonElement jCoordinates = jsonObject.get("coordinates");
        Coordinates coordinates;
        if (jCoordinates.isJsonNull()) {
            coordinates = null;
        }
        else {
            JsonObject jCoordinatesObj = jCoordinates.getAsJsonObject();
            JsonArray jCoorIndices = jCoordinatesObj.get("coordinates").getAsJsonArray();
            ArrayList<Float> coorIndices = new ArrayList<>();
            Iterator<JsonElement> coorIndicesIter = jCoorIndices.iterator();
            while (coorIndicesIter.hasNext()) {
                coorIndices.add(coorIndicesIter.next().getAsFloat());
            }
            String type = jCoordinatesObj.get("type").getAsString();
            coordinates = new Coordinates(coorIndices, type);
        }

        return new Tweet (createdAt, id, text, entities, coordinates, retweetCount, favouriteCount, favourited,
                retweeted);
    }
}
