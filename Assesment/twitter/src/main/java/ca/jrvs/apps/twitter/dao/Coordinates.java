package ca.jrvs.apps.twitter.dao;

import java.util.ArrayList;
import java.util.List;

public class Coordinates {
    private List<Float> coordinates;
    private String type;

    public Coordinates(List<Float> coordinates, String type) {
        this.coordinates = coordinates;
        this.type = type;
    }

    public Coordinates(Float lon, Float lat, String type) {
        coordinates = new ArrayList<>();
        coordinates.add(lon);
        coordinates.add(lat);
        this.type = type;
    }

    public List<Float> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Float> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"coordinates\":" + coordinates + ",\n" +
                "\t\"type\":" + type + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Coordinates && coordinates.equals(((Coordinates) obj).getCoordinates()) &&
                type.equals(((Coordinates) obj).getType()));
    }
}
