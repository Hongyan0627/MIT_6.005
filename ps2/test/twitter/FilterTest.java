package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     * 
     * Testing strategy (following the example of the Testing reading):
     * 
     * For writtenBy()
     * Partitions:
     *    one tweet, multiple tweets
     *    one result, multiple results, no results
     *    lowercase, uppercase
     *    
     * 
     * Each of these parts is covered by at least one test case below.
     * 
     * For inTimespan()
     * Partitions:
     *    one result, multiple results, no results
     *   
     * For containing()
     * Partitions:
     *    empty result, one result, multiple results
     *    lower case match, uppercase match
     * 
     * Each of these parts is covered by at least one test case below.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T14:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T16:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "I don't think that matters. talk, ok? #hype", d3);
    private static final Tweet tweet4 = new Tweet(4, "AlySSa", "This is interesting. This is hard. Who knows?", d4);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByMultipleTweetsMultipleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2,tweet3), "alyssa");
        
        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
    }
    
    @Test
    public void testWrittenByMultipleTweetsMultipleResultUpperCase() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2,tweet3,tweet4), "alyssa");
       
        assertEquals("expected singleton list", 3, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet4));
    }
    
    @Test
    public void testWrittenByMultipleTweetsNoResults() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2,tweet3,tweet4), "hahah");
       
        assertEquals("expected singleton list", 0, writtenBy.size());
        
    }
    
    @Test
    public void testWrittenByMultipleTweetsMultipleResultUpperCase2() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2,tweet3,tweet4), "AlyssA");
       
        assertEquals("expected singleton list", 3, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet4));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T17:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet4, tweet2,tweet1,tweet3), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2,tweet3,tweet4)));
        assertEquals("expected same order", 2, inTimespan.indexOf(tweet1));
        assertEquals("expected same order", 3, inTimespan.indexOf(tweet3));
    }
    
    
    @Test
    public void testInTimespanMultipleTweetsSingleResults() {
        Instant testStart = Instant.parse("2016-02-17T15:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T17:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2,tweet3,tweet4), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet4)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet4));
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    @Test
    public void testNotContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2,tweet3,tweet4), Arrays.asList("supertalk"));
        
        assertTrue("expected non-empty list", containing.isEmpty());
   
    }
    
    @Test
    public void testContainingSingle() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2,tweet3,tweet4), Arrays.asList("hard."));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet4)));
        assertEquals("expected same order", 0, containing.indexOf(tweet4));
   
    }
    
    @Test
    public void testContainingMultiple() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet2, tweet1,tweet3,tweet4), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1,tweet2)));
        assertEquals("expected same order", 1, containing.indexOf(tweet1));
   
    }
    
    @Test
    public void testContainingUppercase() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet2, tweet1,tweet3,tweet4), Arrays.asList("TALK"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1,tweet2)));
        assertEquals("expected same order", 1, containing.indexOf(tweet1));
   
    }
    
    @Test
    public void testContainingMultipleWords() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet2, tweet1,tweet3,tweet4), Arrays.asList("superman","hard","TALK"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1,tweet2)));
        assertEquals("expected same order", 1, containing.indexOf(tweet1));
   
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
