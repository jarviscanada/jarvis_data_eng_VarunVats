package ca.jrvs.apps.twitter.dao;

import java.util.List;

public class UserMention{
    private long id;
    private String id_str;
    private List<Integer> indices;
    private String name;
    private String screenName;

    public UserMention(long id, List<Integer> indices, String name, String screenName) {
        this.id = id;
        id_str = String.valueOf(id);
        this.indices = indices;
        this.name = name;
        this.screenName = screenName;
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

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"name\": \"" + name + "\",\n" +
                "\t\"indices\":" + indices + ",\n" +
                "\t\"screen_name\":\"" + screenName + "\",\n" +
                "\t\"id\":" + id + ",\n" +
                "\t\"id_str\":\"" + id_str + "\"\n" +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof UserMention && id == ((UserMention) obj).getId() &&
                indices.equals(((UserMention) obj).getIndices()) && name.equals(((UserMention) obj).getName()) &&
                screenName.equals(((UserMention) obj).getScreenName()));
    }
}
