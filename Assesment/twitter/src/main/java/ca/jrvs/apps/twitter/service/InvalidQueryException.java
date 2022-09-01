package ca.jrvs.apps.twitter.service;

public class InvalidQueryException extends Exception {
    public InvalidQueryException() {
        super();
    }

    public InvalidQueryException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidQueryException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
