package ca.jrvs.apps.twitter.dao;

import java.util.List;

public class HashTag {
    private List<Integer> indices;
    private String text;

    public HashTag(List<Integer> indices, String text) {
        this.indices = indices;
        this.text = text;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"indices\":" + indices + ",\n" +
                "\t\"text\":" + text + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof HashTag && indices.equals(((HashTag) obj).getIndices()) &&
                text.equals(((HashTag) obj).getText()));
    }
}
