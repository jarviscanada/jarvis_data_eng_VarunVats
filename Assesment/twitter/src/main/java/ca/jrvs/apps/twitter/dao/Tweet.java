package ca.jrvs.apps.twitter.dao;

import java.util.Random;

public class Tweet {
    private String created_at;
    private long id;
    private String id_str;
    private String text;
    private Entities entities;
    private Coordinates coordinates;
    private Integer retweetCount;
    private Integer favouriteCount;
    private Boolean favourited;
    private Boolean retweeted;

    public Tweet (String created_at, long id, String text, Entities entities, Coordinates coordinates,
                  Integer retweetCount, Integer favouriteCount, Boolean favourited, Boolean retweeted) {
        this.created_at = created_at;
        this.id = id;
        id_str = String.valueOf(id);
        this.text = text;
        this.entities = entities;
        this.coordinates = coordinates;
        this.retweetCount = retweetCount;
        this.favouriteCount = favouriteCount;
        this.favourited = favourited;
        this.retweeted = retweeted;
    }

    public Tweet(String text) {
        this.created_at = java.time.Instant.now().toString();
        this.id = new Random().nextLong();
        id_str = String.valueOf(this.id);
        this.text = text;
        entities = new Entities();
        coordinates = new Coordinates(-1f, 1f, "Point");
        retweetCount = 0;
        favouriteCount = 0;
        favourited = false;
        retweeted = false;
    }


    public Tweet(String text, float lon, float lat) {
        this.created_at = java.time.Instant.now().toString();
        this.id = new Random().nextLong();
        id_str = String.valueOf(this.id);
        this.text = text;
        entities = new Entities();
        coordinates = new Coordinates(lon, lat, "Point");
        retweetCount = 0;
        favouriteCount = 0;
        favourited = false;
        retweeted = false;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(Integer retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Integer getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(Integer favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public Boolean isFavourited() {
        return favourited;
    }

    public void setFavourited(Boolean favourited) {
        this.favourited = favourited;
    }

    public Boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"created_at\":" + created_at + ",\n" +
                "\"id\":" + id + ",\n" +
                "\"id_str\":\"" + id_str + "\",\n" +
                "\"text\":\"" + text + "\",\n" +
                "\"entities\":" + entities + ",\n" +
                "\"coordinates\":" + coordinates + ",\n" +
                "\"retweet_count\":" + retweetCount + ",\n" +
                "\"favorite_count\":" + favouriteCount + ",\n" +
                "\"favorited\":" + favourited + ",\n" +
                "\"retweeted\":" + retweeted + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Tweet && created_at.equals(((Tweet) obj).getCreated_at()) &&
                id == ((Tweet) obj).getId() && text.equals(((Tweet) obj).getText()) &&
                entities.equals(((Tweet) obj).getEntities()) && coordinates.equals(((Tweet) obj).getCoordinates()) &&
                retweetCount.equals(((Tweet) obj).getRetweetCount()) &&
                favouriteCount.equals(((Tweet) obj).getFavouriteCount()) &&
                favourited.equals(((Tweet) obj).isFavourited()) && retweeted.equals(((Tweet) obj).isRetweeted()));
    }
}
