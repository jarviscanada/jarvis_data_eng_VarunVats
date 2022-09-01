package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class TwitterDao implements CrdDao<Tweet, String>{
    // URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";

    // URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    // Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) { this.httpHelper = httpHelper; }

    @Override
    public Tweet create(Tweet entity) throws URISyntaxException, UnsupportedEncodingException, NotFoundException {
        // Set up URI for POST operations
        String uriString = API_BASE_URI + POST_PATH + QUERY_SYM + "status" +
                EQUAL + URLEncoder.encode(entity.getText(), StandardCharsets.UTF_8.toString());
        if (entity.getCoordinates() != null) {
            List<Float> coordinates = entity.getCoordinates().getCoordinates();
            uriString = uriString + AMPERSAND + "long" + EQUAL + coordinates.get(0) + AMPERSAND +
                    "lat" + EQUAL + coordinates.get(1);
        }
        URI uri = new URI(uriString);
        // Retrieve tweet from response
        return parseResponseBody(httpHelper.httpPost(uri, null), HTTP_OK);
    }

    @Override
    public Tweet findById(String s) throws URISyntaxException, NotFoundException {
        // Set up URI for GET operations
        URI uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s);
        // Retrieve tweet from response
        return parseResponseBody(httpHelper.httpGet(uri, null), HTTP_OK);
    }

    @Override
    public Tweet deleteById(String s) throws URISyntaxException, NotFoundException {
        // Set up URI for DELETE operations
        URI uri = new URI(API_BASE_URI + DELETE_PATH + "/" + s + ".json");
        // Retrieve tweet from response
        return parseResponseBody(httpHelper.httpPost(uri, null), HTTP_OK);
    }

    protected Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) throws NotFoundException {
        // Check response status
        int status = response.getStatusLine().getStatusCode();
        if (status == 404) {
            throw new NotFoundException();
        }
        if (status != expectedStatusCode) {
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP status:" + status);
        }

        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }

        // Convert Response Entity to String
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to String", e);
        }

        return JsonUtils.getTweetFromJson(jsonStr);
    }

}
