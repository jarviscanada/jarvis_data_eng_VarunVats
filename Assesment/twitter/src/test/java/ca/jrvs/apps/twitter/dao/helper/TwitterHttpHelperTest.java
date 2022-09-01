package ca.jrvs.apps.twitter.dao.helper;

import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TwitterHttpHelperTest {
    private static String API_BASE_URI;
    private static String POST_PATH;
    private static String SHOW_PATH;
    private static String DELETE_PATH;

    TwitterHttpHelper helper;

    @BeforeClass
    public static void classSetUp() {
        API_BASE_URI = "https://api.twitter.com";
        POST_PATH = "/1.1/statuses/update.json";
        SHOW_PATH = "/1.1/statuses/show.json";
        DELETE_PATH = "/1.1/statuses/destroy";
    }

    @Before
    public void setUp() {
        helper = new TwitterHttpHelper();
    }

    @Test
    public void httpPost() throws URISyntaxException {
        Assert.assertNotNull(helper.httpPost(new URI(API_BASE_URI + POST_PATH + "?status=teststring"),
                null));
    }

    @Test
    public void httpGet() throws URISyntaxException {
        Assert.assertNotNull(helper.httpPost(new URI(API_BASE_URI + SHOW_PATH + "?id=1479553878219800581"),
                null));
    }
}