package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	assert(!tweets.isEmpty());
        Instant start = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();
        for(Tweet tw:tweets){
            Instant tmp = tw.getTimestamp();
            if(tmp.isBefore(start)){
                start = tmp;
            }
            if(tmp.isAfter(end)){
                end = tmp;
            }
        }
        Timespan timespan = new Timespan(start,end);
        return timespan;
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> res = new HashSet<String>();
        final String REGEX = "([^a-zA-Z0-9-_]*)(@[a-zA-Z0-9-_]+)([^a-zA-Z0-9-_@s]*)";
        Pattern p = Pattern.compile(REGEX);
        for (Tweet tw:tweets){
            String tmp = tw.getText();
            Matcher m = p.matcher(tmp);
            System.out.println("Tweet is: " + tmp);
            while(m.find()) {
                int index1 = m.start();
                int index2 = m.end();
                String tmpSubstring = tmp.substring(index1, index2);
                
                Pattern p1 = Pattern.compile("@[a-zA-Z0-9-_]+");
                Matcher m1 = p1.matcher(tmpSubstring);
                
                if(m1.find()) { 
                    int start_index = m1.start() + 1;
                    int end_index = m1.end();
                    
                    if((index1 + start_index - 1) > 0){
          
                        String doubleCheck = tmp.substring(0 + index1 + start_index - 2, 1 + index1 + start_index - 2);
                        Pattern p2 = Pattern.compile("[a-zA-Z0-9-_]+");
                        Matcher m2 = p2.matcher(doubleCheck);
                        if(m2.find()) {
                            continue;
                        }
                    }
                    end_index = (end_index > tmpSubstring.length()) ? tmpSubstring.length() : end_index;
                    
                    res.add(tmpSubstring.substring(start_index, end_index).toLowerCase());
                }
            }
        }
        return res;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
