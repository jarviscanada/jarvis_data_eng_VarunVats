package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URI;

@Controller
public class TwitterHttpHelper implements HttpHelper{
    private OAuthConsumer consumer;
    private HttpClient httpClient;

    /*
    @Autowired
    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClientBuilder.create().build();
    }
    */

    @Autowired
    public TwitterHttpHelper() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public HttpResponse httpPost(URI uri, StringEntity stringEntity) {
        try {
            return executeHttpRequest(HttpMethod.POST, uri, stringEntity);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    @Override
    public HttpResponse httpGet(URI uri, StringEntity stringEntity) {
        try {
            return executeHttpRequest(HttpMethod.GET, uri, stringEntity);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to execute", e);
        }
    }

    private HttpResponse executeHttpRequest(HttpMethod method, URI uri, StringEntity stringEntity)
            throws OAuthException, IOException {
        // If the GET method is given
        if (method == HttpMethod.GET) {
            // Create the HTTP GET request
            HttpGet request = new HttpGet(uri);
            // Sign the created request with an OAuth signature, authorizing the request
            consumer.sign(request);
            // Execute the created request
            return httpClient.execute(request);
        }
        // If the POST method is given
        else if (method == HttpMethod.POST) {
            // Create the HTTP POST request
            HttpPost request = new HttpPost(uri);
            if (stringEntity != null) {
                // Set the string to be added
                request.setEntity(stringEntity);
            }
            // Sign the created request with an OAuth signature, authorizing the request
            consumer.sign(request);
            // Execute the created request
            return httpClient.execute(request);
        }
        else {
            throw new IllegalArgumentException("Unknown HTTP method: " + method.name());
        }
    }
}
