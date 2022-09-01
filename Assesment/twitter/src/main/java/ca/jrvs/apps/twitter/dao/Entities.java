package ca.jrvs.apps.twitter.dao;

import java.util.ArrayList;
import java.util.List;

public class Entities {
    private List<HashTag> hashTags;
    private List<UserMention> userMentions;

    public Entities(List<HashTag> hashTags, List<UserMention> userMentions) {
        this.hashTags = hashTags;
        this.userMentions = userMentions;
    }

    public Entities() {
        hashTags = new ArrayList<>();
        userMentions = new ArrayList<>();
    }

    public List<HashTag> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<HashTag> hashTags) {
        this.hashTags = hashTags;
    }

    public List<UserMention> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(List<UserMention> userMentions) {
        this.userMentions = userMentions;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"hashtags\":" + ((hashTags != null) ? hashTags.toString() : "[]") + ",\n" +
                "\t\"user_mentions\":" + ((userMentions != null) ? userMentions.toString() : "[]") + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Entities && hashTags.equals(((Entities) obj).getHashTags()) &&
                userMentions.equals(((Entities) obj).getUserMentions()));
    }
}
