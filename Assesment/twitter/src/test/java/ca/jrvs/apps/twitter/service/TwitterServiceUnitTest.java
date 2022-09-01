package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.JsonUtils;
import ca.jrvs.apps.twitter.dao.NotFoundException;
import ca.jrvs.apps.twitter.dao.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    CrdDao mockDao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweet() throws UnsupportedEncodingException, URISyntaxException, InvalidTweetException,
            NotFoundException {
        // Test valid tweet
        String validTweetJsonStr = "{\n"
                + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "\"id\":1097607853932564480,\n"
                + "\"id_str\":\"1097607853932564480\",\n"
                + "\"text\":\"test with loc223\",\n"
                + "\"entities\":{\n"
                + "\t\"hashtags\":[],\n"
                + "\t\"user_mentions\":[]\n"
                + "},\n"
                + "\"coordinates\": {\n"
                + "\t\"coordinates\": [-1.0,1.0],\n"
                + "\t\"type\":\"Point\"\n"
                + "},\n"
                + "\"retweet_count\":0,\n"
                + "\"favorite_count\":0,\n"
                + "\"favorited\":false,\n"
                + "\"retweeted\":false\n"
                + "}";
        Tweet expectedTweet = JsonUtils.getTweetFromJson(validTweetJsonStr);
        when(mockDao.create(expectedTweet)).thenReturn(expectedTweet);

        TwitterService spyService = Mockito.spy(service);
        Tweet tweet = spyService.postTweet(expectedTweet);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
        assertTrue(tweet.getText().length() < 140);
        assertNotNull(tweet.getCoordinates());
        List<Float> c = tweet.getCoordinates().getCoordinates();
        assertTrue(c.get(0) >= -180 && c.get(0) <= 180);
        assertTrue(c.get(1) >= -90 && c.get(1) <= 90);

        // Test tweet which exceeds 140 characters
        String longTweetJsonStr = "{\n"
                + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "\"id\":1097607853932564480,\n"
                + "\"id_str\":\"1097607853932564480\",\n"
                + "\"text\":\"dklasd;fkjasl;dfjaslkdflaskd;sal;kdgsakbnaskjfhgslkfjmovjsaldvjsiofgdasdg"
                + "asdaskldnalkcnkldsvnaskdfasfglnsdfasdfabgnjdgsadfsafscsvbafdsbdrgsfsbDASFASD\",\n"
                + "\"entities\":{\n"
                + "\t\"hashtags\":[],\n"
                + "\t\"user_mentions\":[]\n"
                + "},\n"
                + "\"coordinates\": {\n"
                + "\t\"coordinates\": [-1.0,1.0],\n"
                + "\t\"type\":\"Point\"\n"
                + "},\n"
                + "\"retweet_count\":0,\n"
                + "\"favorite_count\":0,\n"
                + "\"favorited\":false,\n"
                + "\"retweeted\":false\n"
                + "}";
        expectedTweet = JsonUtils.getTweetFromJson(longTweetJsonStr);
        try {
            spyService.postTweet(expectedTweet);
            fail("InvalidTweetException not thrown");
        } catch (InvalidTweetException e) {}

        // Test tweet with an out of range longitude
        String badLongTweetJsonStr = "{\n"
                + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "\"id\":1097607853932564480,\n"
                + "\"id_str\":\"1097607853932564480\",\n"
                + "\"text\":\"test with loc223\",\n"
                + "\"entities\":{\n"
                + "\t\"hashtags\":[],\n"
                + "\t\"user_mentions\":[]\n"
                + "},\n"
                + "\"coordinates\": {\n"
                + "\t\"coordinates\": [-9999.0,1.0],\n"
                + "\t\"type\":\"Point\"\n"
                + "},\n"
                + "\"retweet_count\":0,\n"
                + "\"favorite_count\":0,\n"
                + "\"favorited\":false,\n"
                + "\"retweeted\":false\n"
                + "}";
        expectedTweet = JsonUtils.getTweetFromJson(badLongTweetJsonStr);
        try {
            spyService.postTweet(expectedTweet);
            fail("InvalidTweetException not thrown");
        } catch (InvalidTweetException e) {}

        // Test tweet with an out of range latitude
        String badLatTweetJsonStr = "{\n"
                + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "\"id\":1097607853932564480,\n"
                + "\"id_str\":\"1097607853932564480\",\n"
                + "\"text\":\"test with loc223\",\n"
                + "\"entities\":{\n"
                + "\t\"hashtags\":[],\n"
                + "\t\"user_mentions\":[]\n"
                + "},\n"
                + "\"coordinates\": {\n"
                + "\t\"coordinates\": [-1.0,9999.0],\n"
                + "\t\"type\":\"Point\"\n"
                + "},\n"
                + "\"retweet_count\":0,\n"
                + "\"favorite_count\":0,\n"
                + "\"favorited\":false,\n"
                + "\"retweeted\":false\n"
                + "}";
        expectedTweet = JsonUtils.getTweetFromJson(badLatTweetJsonStr);
        try {
            spyService.postTweet(expectedTweet);
            fail("InvalidTweetException not thrown");
        } catch (InvalidTweetException e) {}
    }

    @Test
    public void showTweet() throws URISyntaxException, InvalidQueryException, NotFoundException {
        // Test valid id query
        String validTweetJsonStr = "{\n"
                + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "\"id\":1097607853932564480,\n"
                + "\"id_str\":\"1097607853932564480\",\n"
                + "\"text\":\"test with loc223\",\n"
                + "\"entities\":{\n"
                + "\t\"hashtags\":[],\n"
                + "\t\"user_mentions\":[]\n"
                + "},\n"
                + "\"coordinates\": {\n"
                + "\t\"coordinates\": [-1.0,1.0],\n"
                + "\t\"type\":\"Point\"\n"
                + "},\n"
                + "\"retweet_count\":0,\n"
                + "\"favorite_count\":0,\n"
                + "\"favorited\":false,\n"
                + "\"retweeted\":false\n"
                + "}";
        Tweet expectedTweet = JsonUtils.getTweetFromJson(validTweetJsonStr);
        String id = "1097607853932564480";
        when(mockDao.findById(id)).thenReturn(expectedTweet);
        TwitterService spyService = Mockito.spy(service);
        String[] fields = {"text", "created_at", "coordinates"};
        Tweet tweet = spyService.showTweet(id, fields);
        assertNotNull(tweet);
        assertEquals(Long.parseLong(id), Long.parseLong(tweet.getId_str()));
        assertEquals(tweet.getCreated_at(), expectedTweet.getCreated_at());
        assertEquals(tweet.getText(), expectedTweet.getText());
        assertEquals(tweet.getCoordinates(), expectedTweet.getCoordinates());
        assertNull(tweet.getEntities().getHashTags());
        assertNull(tweet.getEntities().getUserMentions());
        assertNull(tweet.getRetweetCount());
        assertNull(tweet.getFavouriteCount());
        assertNull(tweet.isFavourited());
        assertNull(tweet.isRetweeted());

        // Test invalid id query
        String badId = "kdnvsadvnoiaskvnasokv";
        try {
            spyService.showTweet(badId, fields);
            fail("InvalidQueryException not thrown");
        } catch (InvalidQueryException e) {}

        // Test invalid fields query
        String[] badFields = {"sdvjsoabvjaosb"};
        try {
            spyService.showTweet(id, badFields);
            fail("InvalidQueryException not thrown");
        } catch (InvalidQueryException e) {}
    }

    @Test
    public void deleteTweets() throws NotFoundException, URISyntaxException, InvalidQueryException {
        String[] expectedTweetStrings = {"{\n"
                + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "\"id\":1097607853932561254,\n"
                + "\"id_str\":\"1097607853932561254\",\n"
                + "\"text\":\"test with loc283\",\n"
                + "\"entities\":{\n"
                + "\t\"hashtags\":[],\n"
                + "\t\"user_mentions\":[]\n"
                + "},\n"
                + "\"coordinates\": {\n"
                + "\t\"coordinates\": [-1.0,1.0],\n"
                + "\t\"type\":\"Point\"\n"
                + "},\n"
                + "\"retweet_count\":0,\n"
                + "\"favorite_count\":0,\n"
                + "\"favorited\":false,\n"
                + "\"retweeted\":false\n"
                + "}",
                "{\n"
                + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "\"id\":1097607853932563761,\n"
                + "\"id_str\":\"1097607853932563761\",\n"
                + "\"text\":\"test with loc095\",\n"
                + "\"entities\":{\n"
                + "\t\"hashtags\":[],\n"
                + "\t\"user_mentions\":[]\n"
                + "},\n"
                + "\"coordinates\": {\n"
                + "\t\"coordinates\": [-1.0,1.0],\n"
                + "\t\"type\":\"Point\"\n"
                + "},\n"
                + "\"retweet_count\":0,\n"
                + "\"favorite_count\":0,\n"
                + "\"favorited\":false,\n"
                + "\"retweeted\":false\n"
                + "}",
                "{\n"
                + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "\"id\":1097607853932562467,\n"
                + "\"id_str\":\"1097607853932562467\",\n"
                + "\"text\":\"test with loc182\",\n"
                + "\"entities\":{\n"
                + "\t\"hashtags\":[],\n"
                + "\t\"user_mentions\":[]\n"
                + "},\n"
                + "\"coordinates\": {\n"
                + "\t\"coordinates\": [-1.0,1.0],\n"
                + "\t\"type\":\"Point\"\n"
                + "},\n"
                + "\"retweet_count\":0,\n"
                + "\"favorite_count\":0,\n"
                + "\"favorited\":false,\n"
                + "\"retweeted\":false\n"
                + "}"};
        Tweet[] expectedTweets = {JsonUtils.getTweetFromJson(expectedTweetStrings[0]),
                JsonUtils.getTweetFromJson(expectedTweetStrings[1]),
                JsonUtils.getTweetFromJson(expectedTweetStrings[2])};
        when(mockDao.deleteById("1097607853932561254")).thenReturn(expectedTweets[0]);
        when(mockDao.deleteById("1097607853932563761")).thenReturn(expectedTweets[1]);
        when(mockDao.deleteById("1097607853932562467")).thenReturn(expectedTweets[2]);
        TwitterService spyService = Mockito.spy(service);
        String[] ids = {expectedTweets[0].getId_str(), expectedTweets[1].getId_str(),
                expectedTweets[2].getId_str()};
        List<Tweet> tweets = spyService.deleteTweets(ids);
        assertNotNull(tweets);
        assertEquals(tweets.get(0), expectedTweets[0]);
        assertEquals(tweets.get(1), expectedTweets[1]);
        assertEquals(tweets.get(2), expectedTweets[2]);

        // Test invalid id query
        String[] badId = {"kdnvsadvnoiaskvnasokv"};
        try {
            spyService.deleteTweets(badId);
            fail("InvalidQueryException not thrown");
        } catch (InvalidQueryException e) {}
    }
}