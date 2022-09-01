package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.JsonUtils;
import ca.jrvs.apps.twitter.dao.NotFoundException;
import ca.jrvs.apps.twitter.service.InvalidQueryException;
import ca.jrvs.apps.twitter.service.InvalidTweetException;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.dao.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
    // Each method in 'TwitterController' returns the direct result of a method from 'TwitterService'. Hence, since
    // 'TwitterService' is being mocked, nothing in these unit tests actually tests the code written in
    // 'TwitterController''s methods
    @Mock
    Service mockService;

    @InjectMocks
    TwitterController controller;

    @Test
    public void postTweet() throws InvalidTweetException, UnsupportedEncodingException, NotFoundException,
            URISyntaxException {
        String validTweetJsonStr = "{\n"
                + "\"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
                + "\"id\":1097607853932564480,\n"
                + "\"id_str\":\"1097607853932564480\",\n"
                + "\"text\":\"Array is the most #important thing in any programming #language\",\n"
                + "\"entities\":{\n"
                + "\t\"hashtags\":[{\n"
                + "\t\"indices\":[18,28],\n"
                + "\t\"text\":important\n"
                + "},\n"
                + "{\n"
                + "\t\"indices\":[54,63],\n"
                + "\t\"text\":language\n"
                + "}\n"
                + "],\n"
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
        when(mockService.postTweet(any())).thenReturn(expectedTweet);

        String[] args = {"Array is the most #important thing in any programming #language"};
        TwitterController spyController = Mockito.spy(controller);
        Tweet tweet = spyController.postTweet(args);
        System.out.println(tweet);
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
        assertEquals(args[0], tweet.getText());
        assertNotNull(tweet.getEntities().getHashTags());
        assertEquals("important", tweet.getEntities().getHashTags().get(0).getText());
        assertEquals("language", tweet.getEntities().getHashTags().get(1).getText());
    }

    @Test
    public void showTweet() throws NotFoundException, URISyntaxException, InvalidQueryException {
        //fail("Not Implemented");
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
        String text = "test with loc223";
        when(mockService.showTweet(isNotNull(), any())).thenReturn(expectedTweet);

        String[] args = {id, text};
        TwitterController spyController = Mockito.spy(controller);
        Tweet tweet = spyController.showTweet(args);
        System.out.println(tweet);
        assertNotNull(tweet);
        assertNotNull(tweet.getId_str());
        assertEquals(id, tweet.getId_str());
        assertNotNull(tweet.getText());
        assertEquals(text, tweet.getText());
    }

}