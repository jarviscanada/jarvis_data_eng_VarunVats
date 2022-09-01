package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.NotFoundException;
import ca.jrvs.apps.twitter.dao.Tweet;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.service.InvalidQueryException;
import ca.jrvs.apps.twitter.service.InvalidTweetException;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {
    private TwitterController controller;
    public Float lon, lat;

    @Before
    public void setUp(){
        HttpHelper httpHelper = new TwitterHttpHelper();
        TwitterDao dao = new TwitterDao(httpHelper);
        TwitterService service = new TwitterService(dao);
        controller = new TwitterController(service);
        lat = 1f;
        lon = -1f;
    }

    @Test
    public void postTweet() throws InvalidTweetException, UnsupportedEncodingException, NotFoundException,
            URISyntaxException {
        String[] args = {"Array is the most #important thing in any programming #language " + System.currentTimeMillis()};
        Tweet tweet = controller.postTweet(args);
        System.out.println(tweet);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
        assertEquals(args[0], tweet.getText());
        assertNotNull(tweet.getEntities().getHashTags());
        assertEquals("important", tweet.getEntities().getHashTags().get(0).getText());
        assertEquals("language", tweet.getEntities().getHashTags().get(1).getText());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void showTweet() throws NotFoundException, URISyntaxException, InvalidQueryException {
        String[] args = {"1479553878219800581", "text"};
        Tweet tweet = controller.showTweet(args);
        System.out.println(tweet);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
        assertEquals("test post", tweet.getText());
    }

    @Test
    public void deleteTweet() throws InvalidTweetException, UnsupportedEncodingException, NotFoundException,
            URISyntaxException, InvalidQueryException {

        String[] text1 = {"To be deleted1"};
        String[] text2 = {"To be deleted2"};
        String[] text3 = {"To be deleted3"};

        Tweet tweet1 = controller.postTweet(text1);
        Tweet tweet2 = controller.postTweet(text2);
        Tweet tweet3 = controller.postTweet(text3);

        String id1 = tweet1.getId_str();
        String id2 = tweet2.getId_str();
        String id3 = tweet3.getId_str();

        String[] ids = {id1, id2, id3};

        List<Tweet> tweets = controller.deleteTweet(ids);
        assertNotNull(tweets);
        assertEquals(id1, tweets.get(0).getId_str());
        assertEquals(text1[0], tweets.get(0).getText());
        assertEquals(id2, tweets.get(1).getId_str());
        assertEquals(text2[0], tweets.get(1).getText());
        assertEquals(id3, tweets.get(2).getId_str());
        assertEquals(text3[0], tweets.get(2).getText());
    }
}