package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     * 
     * Testing strategy (following the example of the Testing reading):
     * 
     * For getTimespan()
     * Partitions:
     *    tweet list length == 1, tweet list length == 2, tweet list length > 2
     *    same time stamps, different timestamps
     * Also check input list is not changed
     *    
     *    
     * For getMentionedUsers()
     * Partitions:
     *    tweet list length == 1, tweet list length == 2, tweet list length > 2
     *    no users mentions, some users mentioned
     *    has valid @, has invalid @
     *    has repeated mentioned users(lower case and lower case, lower case and 
     *           upper case), has no repeated mentioned users
     *    continuous @, discrete @
     * Also check input list is not changed   
     * Each of these parts is covered by at least one test case below.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T13:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "chaodhi", "Today is pretty good!", d3);
    private static final Tweet tweet4 = new Tweet(4, "dddd", "weibo is good", d4);
    
    
    private static final Tweet tweet5 = new Tweet(5, "eeee", "I don't know what to say", d2);
    private static final Tweet tweet6 = new Tweet(6, "ffff", "hahaahh", d2);
    private static final Tweet tweet7 = new Tweet(7, "gggg", "I don't know what to say @user1", d2);
    private static final Tweet tweet8 = new Tweet(8, "hhh", "@user2 what do you think?", d2);
    private static final Tweet tweet9 = new Tweet(9, "iii", "@@@_tricker-User-1.This text woul @@user5@ @@user3@@user4@ be su@dddd super tricky, ?@@alyssa] tufss I don't think @@__--This-is_a_user?@@__--this-is_a_user.?", d2);
    private static final Tweet tweet10 = new Tweet(10, "iii", "@@@_tricker-User-1.This text woul @@user5@user5@ @@user3@@user4@ be su@dddd super tricky, ?@@alyssa] tufss I don't think @@__--This-is_a_user?@@__--this-is_a_user.?", d2);
    private static final Tweet tweet11 = new Tweet(11, "iii", "@user1 is talking about somethin @user2", d2);
    private static final Tweet tweet12 = new Tweet(12, "iii", "@uSer1 is talking about somethin @uSer2", d2);
    private static final Tweet tweet13 = new Tweet(13, "iii", "@uSer1@uSer2 is talking about somethin", d2);
    private static final Tweet tweet14 = new Tweet(14, "iii", "@user1, @user1 is talking about somethin", d2);
    private static final Tweet tweet15 = new Tweet(15, "iii", "abc@user1,is talking about somethin", d2);
    private static final Tweet tweet16 = new Tweet(16, "iii", "abc @user3,is talking about somethin", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespan1() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespan2() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespan3() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet5));
        
        assertEquals("expected start", d2, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespan4() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespan5() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet5, tweet6));
        
        assertEquals("expected start", d2, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespan6() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet4, tweet5, tweet6));
        
        assertEquals("expected start", d2, timespan.getStart());
        assertEquals("expected end", d4, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespan7() {
        Extract.getTimespan(Arrays.asList(tweet4, tweet5, tweet6));
        
        assertEquals("input tweet is not modified", 4, tweet4.getId());
        assertEquals("input tweet is not modified", 5, tweet5.getId());
        assertEquals("input tweet is not modified", 6, tweet6.getId());
    }
    
    @Test
    public void testGetMentioned1() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentioned2() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet8));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user2 is mentioned", mentionedUsers.contains("user2") || mentionedUsers.contains("USER2"));
    }
    
    @Test
    public void testGetMentioned3() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet11));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1") || mentionedUsers.contains("uSer2"));
        assertTrue("user2 is mentioned", mentionedUsers.contains("user2") || mentionedUsers.contains("USER2") || mentionedUsers.contains("uSer2"));
    }
    
    @Test
    public void testGetMentioned4() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet12));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1"));
        assertTrue("user2 is mentioned", mentionedUsers.contains("user2") || mentionedUsers.contains("USER2"));
    }
    
    @Test
    public void testGetMentioned5() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet13));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1"));
        assertTrue("user2 is not mentioned", !(mentionedUsers.contains("user2") || mentionedUsers.contains("USER2")));
    }
    
    @Test
    public void testGetMentioned6() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet14));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1"));
        assertTrue("Doesn't contain repeated users", mentionedUsers.size() == 1);
    }
    
    @Test
    public void testGetMentioned7() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1,tweet15));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentioned8() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet8));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user2 is mentioned", mentionedUsers.contains("user2") || mentionedUsers.contains("USER2"));
        assertTrue("Doesn't contain repeated users", mentionedUsers.size() == 1);
    }
    
    @Test
    public void testGetMentioned9() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet12,tweet13));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1") || mentionedUsers.contains("uSer2"));
        assertTrue("user2 is mentioned", mentionedUsers.contains("user2") || mentionedUsers.contains("USER2") || mentionedUsers.contains("uSer2"));
        assertTrue("Doesn't contain repeated users", mentionedUsers.size() == 2);
    }
    
    @Test
    public void testGetMentioned10() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet12,tweet16));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1"));
        assertTrue("user2 is mentioned", mentionedUsers.contains("user2") || mentionedUsers.contains("USER2"));
        assertTrue("user3 is mentioned", mentionedUsers.contains("user3") || mentionedUsers.contains("USER3"));
        assertTrue("Doesn't contain repeated users", mentionedUsers.size() == 3);
    }
    
    @Test
    public void testGetMentioned11() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet13,tweet16));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1"));
        assertTrue("user2 is not mentioned", !(mentionedUsers.contains("user2") || mentionedUsers.contains("USER2")));
        assertTrue("user3 is mentioned", mentionedUsers.contains("user3") || mentionedUsers.contains("USER3"));
        assertTrue("Doesn't contain repeated users", mentionedUsers.size() == 2);
    }
    
    @Test
    public void testGetMentioned12() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1,tweet14));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1"));
        assertTrue("Doesn't contain repeated users", mentionedUsers.size() == 1);
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersSomeMention() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7,tweet8));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1"));
        assertTrue("user2 is mentioned", mentionedUsers.contains("user2") || mentionedUsers.contains("USER2"));
        assertTrue("Doesn't contain repeated users", mentionedUsers.size() == 2);
    }
    
    @Test
    public void testGetMentionedUsersMultipleUsersTogether() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet9));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertEquals("expected set size", 6, mentionedUsers.size());
        assertTrue("_tricker-user-1 is mentioned", mentionedUsers.contains("_tricker-user-1") || mentionedUsers.contains("_TRICKER-USER-1"));
        assertTrue("alyssa is mentioned", mentionedUsers.contains("alyssa") || mentionedUsers.contains("ALYSSA"));
        assertTrue("__--this-is_a_user is mentioned", mentionedUsers.contains("__--this-is_a_user") || mentionedUsers.contains("__--THIS-IS_A_USER"));
        assertTrue("user3 is mentioned", mentionedUsers.contains("user3") || mentionedUsers.contains("USER3"));
        assertTrue("user4 is mentioned", mentionedUsers.contains("user4") || mentionedUsers.contains("USER4"));
        assertTrue("user5 is mentioned", mentionedUsers.contains("user5") || mentionedUsers.contains("USER5"));
    }
    
    @Test
    public void testGetMentionedUsersMultipleUsersUpperLowerLetters() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet9));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertEquals("expected set size", 6, mentionedUsers.size());
        assertTrue("_tricker-user-1 is mentioned", mentionedUsers.contains("_tricker-user-1") || mentionedUsers.contains("_TRICKER-USER-1"));
        assertTrue("alyssa is mentioned", mentionedUsers.contains("alyssa") || mentionedUsers.contains("ALYSSA"));
        assertTrue("__--this-is_a_user is mentioned", mentionedUsers.contains("__--this-is_a_user") || mentionedUsers.contains("__--THIS-IS_A_USER"));
        assertTrue("user3 is mentioned", mentionedUsers.contains("user3") || mentionedUsers.contains("USER3"));
        assertTrue("user4 is mentioned", mentionedUsers.contains("user4") || mentionedUsers.contains("USER4"));
        assertTrue("user5 is mentioned", mentionedUsers.contains("user5") || mentionedUsers.contains("USER5"));
    }
    
    @Test
    public void testGetMentionedUsersMultipleUsersRepeatedUsers() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet9,tweet10));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertEquals("expected set size", 6, mentionedUsers.size());
        assertTrue("_tricker-user-1 is mentioned", mentionedUsers.contains("_tricker-user-1") || mentionedUsers.contains("_TRICKER-USER-1"));
        assertTrue("alyssa is mentioned", mentionedUsers.contains("alyssa") || mentionedUsers.contains("ALYSSA"));
        assertTrue("__--this-is_a_user is mentioned", mentionedUsers.contains("__--this-is_a_user") || mentionedUsers.contains("__--THIS-IS_A_USER"));
        assertTrue("user3 is mentioned", mentionedUsers.contains("user3") || mentionedUsers.contains("USER3"));
        assertTrue("user4 is mentioned", mentionedUsers.contains("user4") || mentionedUsers.contains("USER4"));
        assertTrue("user5 is mentioned", mentionedUsers.contains("user5") || mentionedUsers.contains("USER5"));
    }
    
    @Test
    public void testGetMentionedUsersTricky1() {
        Set<String> origin_mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7, tweet8, tweet9));
        Set<String> mentionedUsers = new HashSet<String>();
        for (String str:origin_mentionedUsers) {
        	mentionedUsers.add(str.toLowerCase());
        }
        assertEquals("expected set size", 8, mentionedUsers.size());
        assertTrue("user1 is mentioned", mentionedUsers.contains("user1") || mentionedUsers.contains("USER1"));
        assertTrue("user2 is mentioned", mentionedUsers.contains("user2") || mentionedUsers.contains("USER2"));
        assertTrue("_tricker-user-1 is mentioned", mentionedUsers.contains("_tricker-user-1") || mentionedUsers.contains("_TRICKER-USER-1"));
        assertTrue("alyssa is mentioned", mentionedUsers.contains("alyssa") || mentionedUsers.contains("ALYSSA"));
        assertTrue("__--this-is_a_user is mentioned", mentionedUsers.contains("__--this-is_a_user") || mentionedUsers.contains("__--THIS-IS_A_USER"));
        assertTrue("user3 is mentioned", mentionedUsers.contains("user3") || mentionedUsers.contains("USER3"));
        assertTrue("user4 is mentioned", mentionedUsers.contains("user4") || mentionedUsers.contains("USER4"));
        assertTrue("user5 is mentioned", mentionedUsers.contains("user5") || mentionedUsers.contains("USER5"));
    }
    

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
