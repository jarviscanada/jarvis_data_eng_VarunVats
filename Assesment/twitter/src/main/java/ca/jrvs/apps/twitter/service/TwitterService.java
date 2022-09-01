package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TwitterService implements Service {
    private CrdDao dao;

    @Autowired
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) throws UnsupportedEncodingException, URISyntaxException,
            InvalidTweetException, NotFoundException {
        // Determine if text length is greater than 140 characters
        if (tweet.getText().length() > 140) {
            throw new InvalidTweetException("Tweet text exceeds 140 characters");
        }
        List<Float> coordinates;
        // Determine if tweet coordinates are valid
        try {
            coordinates = tweet.getCoordinates().getCoordinates();
        } catch (NullPointerException e) {
            throw new InvalidTweetException("No longitude/latitude found");
        }
        if (coordinates.get(0) < -180 || coordinates.get(0) > 180) {
            throw new InvalidTweetException("Longitude out of range");
        }
        if (coordinates.get(1) < -90 || coordinates.get(1) > 90) {
            throw new InvalidTweetException("Latitude out of range");
        }
        return (Tweet) dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) throws InvalidQueryException, URISyntaxException,
            NotFoundException {
        // Determine if the id is in a valid format
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidQueryException("Invalid id format");
        }
        return selectFields((Tweet) dao.findById(id), fields);
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) throws URISyntaxException, InvalidQueryException {
        ArrayList<Tweet> tweetList = new ArrayList<>();
        // Iterate through the given array of ids and delete their respective tweets if possible
        for (int i = 0; i < ids.length; i++) {
            try {
                Long.parseLong(ids[i]);
            } catch (NumberFormatException e) {
                throw new InvalidQueryException("Invalid id format");
            }
            try {
                tweetList.add((Tweet) dao.deleteById(ids[i]));
            } catch (NotFoundException e) {}
        }
        return tweetList;
    }

    protected Tweet selectFields(Tweet tweet, String[] fields) throws InvalidQueryException {
        // Initialize Tweet fields
        String created_at = null;
        String text = null;
        Entities entities = new Entities(null, null);
        Coordinates coordinates = null;
        Integer retweetCount = null;
        Integer favouriteCount = null;
        Boolean favourited = null;
        Boolean retweeted = null;

        // Filter out which fields are being queried
        for (String s : fields) {
            if (s.equals("created_at")) {
                created_at = tweet.getCreated_at();
            }
            else if (s.equals("text")) {
                text = tweet.getText();
            }
            else if (s.equals("hashtags")) {
                entities.setHashTags(tweet.getEntities().getHashTags());
            }
            else if (s.equals("user_mentions")) {
                entities.setUserMentions(tweet.getEntities().getUserMentions());
            }
            else if (s.equals("coordinates")) {
                coordinates = new Coordinates(tweet.getCoordinates().getCoordinates(),
                        tweet.getCoordinates().getType());
            }
            else if (s.equals("retweet_count")) {
                retweetCount = tweet.getRetweetCount();
            }
            else if (s.equals("favorite_count")) {
                favouriteCount = tweet.getFavouriteCount();
            }
            else if (s.equals("favorited")) {
                favourited = tweet.isFavourited();
            }
            else if (s.equals("retweeted")) {
                retweeted = tweet.isRetweeted();
            }
            else {
                throw new InvalidQueryException("Invalid field query");
            }
        }

        // Return the tweet with the requested fields
        return new Tweet(created_at, tweet.getId(), text, entities, coordinates, retweetCount, favouriteCount,
                favourited, retweeted);
    }
}
