/**
 * This class is used to collect tweets with a specific hashtag
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Hashtag extends Params {

	private String hashtag ;

	public Hashtag(String hashtag, Twitter twitter) {
		super();
		this.twitter = twitter;
		this.hashtag = hashtag;
	}
	
	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	
	public void startRequest() {
		Query query = new Query("#" + hashtag);
		QueryResult result;
		try {
			result = twitter.search(query);
			for (Status status : result.getTweets()) {
				System.out.println("\n@" + status.getUser().getScreenName() + ":" + status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}
	
}
