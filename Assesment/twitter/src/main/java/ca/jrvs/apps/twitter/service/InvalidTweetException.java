package ca.jrvs.apps.twitter.service;

public class InvalidTweetException extends Exception {
    public InvalidTweetException() {
        super();
    }

    public InvalidTweetException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidTweetException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
