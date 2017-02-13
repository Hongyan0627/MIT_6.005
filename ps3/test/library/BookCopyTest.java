package library;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy
     * ==================
     * 
     * TODO: your testing strategy for this ADT should go here.
     * Make sure you have partitions.
     */
    
    // TODO: put JUnit @Test methods here that you developed from your testing strategy
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testBookCopygetTitle1() {
        Book book = new Book("S", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        assertEquals("S", originbook.getTitle());
    }
    
    @Test
    public void testBookCopygetTitle2() {
        Book book = new Book("a", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        assertEquals("a", originbook.getTitle());
    }
    
    @Test
    public void testBookCopygetTitle3() {
        Book book = new Book("Snow", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        assertEquals("Snow", originbook.getTitle());
    }
    
    @Test
    public void testBookCopygetTitle4() {
        Book book = new Book("Snow is ", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        assertEquals("Snow is ", originbook.getTitle());
    }
    
    @Test
    public void testBookCopygetTitle5() {
        Book book = new Book(" Snow is ", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        assertEquals(" Snow is ", originbook.getTitle());
    }
    
    @Test
    public void testBookCopygetYear1() {
        Book book = new Book(" Snow is ", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1991);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        assertEquals(1991, originbook.getYear());
    }
    
    @Test
    public void testBookCopygetYear2() {
        Book book = new Book(" Snow is ", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1991);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        assert originbook.getYear() >= 0;
    }
    
    @Test
    public void testBookCopygetAuthors1() {
        Book book = new Book(" Snow is ", Arrays.asList("Maxwell", "Tufts", "Harvard"), 1992);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        List<String> authors = originbook.getAuthors();
        assert authors.contains("Maxwell");
        assert authors.contains("Tufts");
        assert authors.contains("Harvard");
    }
    
    @Test
    public void testBookCopygetAuthors2() {
        Book book = new Book(" Snow is ", Arrays.asList(" Max well", " Tuf ts", " Har vard "), 1992);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        List<String> authors = originbook.getAuthors();
        assert authors.contains(" Max well");
        assert authors.contains(" Tuf ts");
        assert authors.contains(" Har vard ");
    }
    
    @Test
    public void testBookCopygetAuthors3() {
        Book book = new Book(" Snow is ", Arrays.asList(" Max well", " Tuf ts", " Har vard "), 1992);
        BookCopy copy = new BookCopy(book);
        Book originbook = copy.getBook();
        List<String> authors1 = originbook.getAuthors();
        assert authors1.contains(" Max well");
        assert authors1.contains(" Tuf ts");
        assert authors1.contains(" Har vard ");
        
        authors1.add("Ben");
        
        List<String> authors2 = originbook.getAuthors();
        assert !authors2.contains("Ben");
    }
    
    @Test
    public void testCondition() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(BookCopy.Condition.GOOD, copy.getCondition());
    }
    
    @Test
    public void testCondition2() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        copy.setCondition(BookCopy.Condition.DAMAGED);
        assertEquals(BookCopy.Condition.DAMAGED, copy.getCondition());
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
