package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.HashTag;
import ca.jrvs.apps.twitter.dao.NotFoundException;
import ca.jrvs.apps.twitter.dao.Tweet;
import ca.jrvs.apps.twitter.service.InvalidQueryException;
import ca.jrvs.apps.twitter.service.InvalidTweetException;
import ca.jrvs.apps.twitter.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller {
    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    @Autowired
    public TwitterController(Service service) {
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) throws InvalidTweetException, UnsupportedEncodingException,
            NotFoundException, URISyntaxException {
        // Expected argument format: [text]
        String text = args[0];
        // Search text for hashtags
        Pattern pat = Pattern.compile("#(\\S+)");
        Matcher mat = pat.matcher(text);
        List<HashTag> hashTags = new ArrayList<>();
        while (mat.find()) {
            String hashTagText = mat.group(1);
            int index = text.indexOf(hashTagText);
            List<Integer> indices = new ArrayList<>();
            indices.add(index);
            indices.add(index + hashTagText.length());
            hashTags.add(new HashTag(indices, hashTagText));
        }
        Tweet tweet = new Tweet(text);
        tweet.getEntities().setHashTags(hashTags);
        return service.postTweet(tweet);
    }

    @Override
    public Tweet showTweet(String[] args) throws NotFoundException, URISyntaxException, InvalidQueryException {
        // Expected argument format: [id] [field1] [field2] [field3] ...
        String id = args[0];
        String[] fields = Arrays.copyOfRange(args, 1, args.length);
        return service.showTweet(id, fields);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) throws URISyntaxException, InvalidQueryException {
        // Expected argument format: [id1] [id2] [id3] ...
        return service.deleteTweets(args);
    }
}
