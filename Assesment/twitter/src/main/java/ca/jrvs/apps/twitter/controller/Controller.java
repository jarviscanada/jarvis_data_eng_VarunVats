package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.NotFoundException;
import ca.jrvs.apps.twitter.dao.Tweet;
import ca.jrvs.apps.twitter.service.InvalidQueryException;
import ca.jrvs.apps.twitter.service.InvalidTweetException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

public interface Controller {

  /**
   * Parse user argument and post a tweet by calling service classes
   *
   * @param args
   * @return a posted tweet
   * @throws IllegalArgumentException if args are invalid
   */
  Tweet postTweet(String[] args) throws InvalidTweetException, UnsupportedEncodingException, NotFoundException, URISyntaxException;

  /**
   * Parse user argument and search a tweet by calling service classes
   *
   * @param args
   * @return a tweet
   * @throws IllegalArgumentException if args are invalid
   */
  Tweet showTweet(String[] args) throws NotFoundException, URISyntaxException, InvalidQueryException;

  /**
   * Parse user argument and delete tweets by calling service classes
   *
   * @param args
   * @return a list of deleted tweets
   * @throws IllegalArgumentException if args are invalid
   */
  List<Tweet> deleteTweet(String[] args) throws URISyntaxException, InvalidQueryException;

}
