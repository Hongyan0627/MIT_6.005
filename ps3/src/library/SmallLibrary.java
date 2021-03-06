package library;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // This rep is required! 
    // Do not change the types of inLibrary or checkedOut, 
    // and don't add or remove any other fields.
    // (BigLibrary is where you can create your own rep for
    // a Library implementation.)

    // rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    
    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out

    // TODO: safety from rep exposure argument
    
    public SmallLibrary() {
        inLibrary = new HashSet<BookCopy>();
        checkedOut = new HashSet<BookCopy>();
    
    }
    
    // assert the rep invariant
    private void checkRep() {
        for(BookCopy bc1:inLibrary) {
        	for(BookCopy bc2:checkedOut) {
        		assert !bc1.equals(bc2);
        	}
        }
    }

    @Override
    public BookCopy buy(Book book) {
        BookCopy copy = new BookCopy(book);
        inLibrary.add(copy);
        return copy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        inLibrary.remove(copy);
        checkedOut.add(copy);
    }
    
    @Override
    public void checkin(BookCopy copy) {
        checkedOut.remove(copy);
        inLibrary.add(copy);
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return inLibrary.contains(copy);
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        Set<BookCopy> allcopies = new HashSet<BookCopy>();
        for(BookCopy bc:inLibrary) {
        	allcopies.add(bc);
        }
        
        for(BookCopy bc:checkedOut) {
        	allcopies.add(bc);
        }
        
        return allcopies;
    }
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
    	Set<BookCopy> availablecopies = new HashSet<BookCopy>();
        for(BookCopy bc:inLibrary) {
        	availablecopies.add(bc);
        }
        
        return availablecopies;
    }

    @Override
    public List<Book> find(String query) {
        throw new RuntimeException("not implemented yet");
    }
    
    @Override
    public void lose(BookCopy copy) {
        throw new RuntimeException("not implemented yet");
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
    // @Override
    // public boolean equals(Object that) {
    //     throw new RuntimeException("not implemented yet");
    // }
    // 
    // @Override
    // public int hashCode() {
    //     throw new RuntimeException("not implemented yet");
    // }
    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
