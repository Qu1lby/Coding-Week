/**
 * This class is used to collect tweets with a specific keyword
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.*;

public class KeyWord extends Params {

	private String keyword;

	/**
	 * Constructor
	 * @param keyword : Keyword searched
	 * @param twitter : Twitter object
	 */
	public KeyWord(String keyword, Twitter twitter) {
		super();
		this.twitter = twitter;
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	/**
	 *  Get Tweets from a keyword and a keyword
	 */
	public void startRequest() {
		Query query = new Query(keyword);
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
