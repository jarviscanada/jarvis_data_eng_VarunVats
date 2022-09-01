package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.NotFoundException;
import ca.jrvs.apps.twitter.dao.Tweet;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {
    private TwitterService service;
    public Float lon, lat;

    @Before
    public void setUp() {
        HttpHelper httpHelper = new TwitterHttpHelper();
        TwitterDao dao = new TwitterDao(httpHelper);
        service = new TwitterService(dao);
        lat = 1f;
        lon = -1f;
        // Need to determine how to create a tweet and obtain the Twitter ID of the immediately created tweet to test
        // deletion.
    }

    @Test
    public void postTweet() throws InvalidTweetException, UnsupportedEncodingException, URISyntaxException,
            NotFoundException {
        String hashTag = "#abc";
        String text = "sometext" + hashTag + System.currentTimeMillis();

        Tweet postTweet = new Tweet (text);
        System.out.println(postTweet);

        Tweet tweet = service.postTweet(postTweet);

        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void showTweet() throws URISyntaxException, InvalidQueryException, NotFoundException {
        String id = "1479553878219800581";
        String[] fields = {"text", "created_at"};
        Tweet tweet = service.showTweet(id, fields);
        assertNotNull(tweet);
        assertEquals(Long.parseLong(id), Long.parseLong(tweet.getId_str()));
        assertEquals("test post", tweet.getText());
        assertNull(tweet.getCoordinates());
        assertNull(tweet.getEntities().getHashTags());
        assertNull(tweet.getEntities().getUserMentions());
        assertNull(tweet.getRetweetCount());
        assertNull(tweet.getFavouriteCount());
        assertNull(tweet.isFavourited());
        assertNull(tweet.isRetweeted());
    }

    @Test
    public void deleteTweets() throws URISyntaxException, InvalidQueryException, InvalidTweetException, UnsupportedEncodingException, NotFoundException {
        String text1 = "To be deleted1";
        String text2 = "To be deleted2";
        String text3 = "To be deleted3";

        Tweet tweet1 = service.postTweet(new Tweet(text1));
        Tweet tweet2 = service.postTweet(new Tweet(text2));
        Tweet tweet3 = service.postTweet(new Tweet(text3));

        String id1 = tweet1.getId_str();
        String id2 = tweet2.getId_str();
        String id3 = tweet3.getId_str();

        String[] ids = {id1, id2, id3};
        List<Tweet> tweets = service.deleteTweets(ids);
        assertNotNull(tweets);
        assertEquals(id1, tweets.get(0).getId_str());
        assertEquals(text1, tweets.get(0).getText());
        assertEquals(id2, tweets.get(1).getId_str());
        assertEquals(text2, tweets.get(1).getText());
        assertEquals(id3, tweets.get(2).getId_str());
        assertEquals(text3, tweets.get(2).getText());
    }
}