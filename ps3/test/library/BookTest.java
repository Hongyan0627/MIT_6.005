package library;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     * 
     * TODO: your testing strategy for this ADT should go here.
     * Make sure you have partitions.
     * Testing strategy for getTitle()
     * 
     * title length == 1, title length >= 2
     * title length contains space, does not contain space
     * title contains upper letters, lower letters
     * 
     * 
     * Testing strategy for getYear()
     * 
     * year is non-negative
     * year is same to immutable
     */
    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("This Test Is Just An Example", book.getTitle());
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testgetTitle1() {
        Book book = new Book("S", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("S", book.getTitle());
    }
    
    @Test
    public void testgetTitle2() {
        Book book = new Book("a", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("a", book.getTitle());
    }
    
    @Test
    public void testgetTitle3() {
        Book book = new Book("Snow", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("Snow", book.getTitle());
    }
    
    @Test
    public void testgetTitle4() {
        Book book = new Book("Snow is ", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("Snow is ", book.getTitle());
    }
    
    @Test
    public void testgetTitle5() {
        Book book = new Book(" Snow is ", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals(" Snow is ", book.getTitle());
    }
    
    @Test
    public void testgetYear1() {
        Book book = new Book(" Snow is ", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1991);
        assertEquals(1991, book.getYear());
    }
    
    @Test
    public void testgetYear2() {
        Book book = new Book(" Snow is ", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1991);
        assert book.getYear() >= 0;
    }
    
    @Test
    public void testgetAuthors1() {
        Book book = new Book(" Snow is ", Arrays.asList("Maxwell", "Tufts", "Harvard"), 1992);
        List<String> authors = book.getAuthors();
        assert authors.contains("Maxwell");
        assert authors.contains("Tufts");
        assert authors.contains("Harvard");
    }
    
    @Test
    public void testgetAuthors2() {
        Book book = new Book(" Snow is ", Arrays.asList(" Max well", " Tuf ts", " Har vard "), 1992);
        List<String> authors = book.getAuthors();
        assert authors.contains(" Max well");
        assert authors.contains(" Tuf ts");
        assert authors.contains(" Har vard ");
    }
    
    @Test
    public void testgetAuthors3() {
        Book book = new Book(" Snow is ", Arrays.asList(" Max well", " Tuf ts", " Har vard "), 1992);
        List<String> authors1 = book.getAuthors();
        assert authors1.contains(" Max well");
        assert authors1.contains(" Tuf ts");
        assert authors1.contains(" Har vard ");
        
        authors1.add("Ben");
        
        List<String> authors2 = book.getAuthors();
        assert !authors2.contains("Ben");
    }
    
    @Test
    public void testgetAuthors4() {
        Book book = new Book(" snow is ", Arrays.asList(" max well", " Tuf ts", " Har vard "), 1992);
        List<String> authors1 = book.getAuthors();
        assert !authors1.contains(" Max well");
        assert authors1.contains(" Tuf ts");
        assert authors1.contains(" Har vard ");
        assert authors1.contains(" max well");
        
        authors1.add("Ben");
        
        List<String> authors2 = book.getAuthors();
        assert !authors2.contains("Ben");
    }
    
    @Test
    public void testgetAuthors5() {
        Book book = new Book(" snow is ", Arrays.asList(" max well", " Tuf ts", " Har vard "), 1992);
        List<String> authors1 = book.getAuthors();
        assert !authors1.contains(" Max well");
        assert authors1.contains(" Tuf ts");
        assert authors1.contains(" Har vard ");
        assert authors1.contains(" max well");
        
        authors1.add("Ken");
        
        List<String> authors2 = book.getAuthors();
        assert !authors2.contains("Ken");
    }
    
    @Test
    public void testgetAuthors6() {
        Book book = new Book(" snow is ", Arrays.asList(" max well", " Tuf ts", " Har vard "), 1992);
        List<String> authors1 = book.getAuthors();
        assert !authors1.contains(" Max well");
        assert authors1.contains(" Tuf ts");
        assert authors1.contains(" Har vard ");
        assert authors1.contains(" max well");
        
        authors1.add("Ken");
        authors1.add("ken");
        
        List<String> authors2 = book.getAuthors();
        assert !authors2.contains("Ken");
        assert !authors2.contains("ken");
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
