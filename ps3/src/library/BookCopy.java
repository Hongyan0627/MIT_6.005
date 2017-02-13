package library;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {

    // TODO: rep
    
	//rep invariant:
	//   book is the original book of this copy
	//   cond is the condition of this book copy
	//abstraction function:
    //   represents a copy of some book with title, publication year and authors
	//safety from rep exposure argument:
    //   all fields are private
	//   there is only one setter to change the condition of the book
    
    public static enum Condition {
        GOOD, DAMAGED
    };
    
    private Condition cond;
    private Book book;
    
    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) {
        this.book = book;
        cond = Condition.GOOD;
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert book.getYear() >= 0;
        assert book.getAuthors().size() >= 1;
        assert book.getTitle().length() >= 1;
    }
    
    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        return book;
    }
    
    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        return cond;
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        cond = condition;
    }
    
    /**
     * @return human-readable representation of this book that includes book.toString()
     *    and the words "good" or "damaged" depending on its condition
     */
    public String toString() {
    	String res = "A copy of book ";
        res += book.getTitle();
        for(String au:book.getAuthors()) {
        	res += (", " + au);
        }
        res += (", "  + Integer.toString(book.getYear()));
        return res;
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
     @Override
     public boolean equals(Object that) {
    	 BookCopy thatbook = (BookCopy)that;
         return (book.equals(thatbook.getBook()) && cond.equals(thatbook.getCondition()));
     }
     
     @Override
     public int hashCode() {
         return book.hashCode() + cond.hashCode();
     }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
