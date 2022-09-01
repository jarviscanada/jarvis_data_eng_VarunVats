package ca.jrvs.apps.twitter.dao;

public class NotFoundException extends Exception {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
