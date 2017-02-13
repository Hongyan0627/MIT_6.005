package library;

import java.util.List;
import java.util.ArrayList;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

    // TODO: rep
    
    //rep invariant:
	//   title is a book's title(nonempty string, contains at least one non-space character)
	//   year is an integer year
	//   authors contains all the authors of the book
    //abstraction function:
	//   represents a book with title, publication year and authors
    //safety from rep exposure argument:
	//   all fields are private and final
	//   there are only public geters, not setters.
	//   authors is mutable list, so Book() constructor and getAuthors() make defensive copies.
    private final String title;
    private final int year;
    private final ArrayList<String> authors; 
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
        this.title = title;
        this.year = year;
        this.authors = new ArrayList<String>();
        for(String au:authors) {
        	this.authors.add(au);
        }
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert authors.size() >= 1;
        assert title.length() >= 1;
        assert year >= 0;
    }
    
    /**
     * @return the title of this book
     */
    public String getTitle() {
    	return this.title;
    }
    
    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
    	List<String> tmp = new ArrayList<String>();
    	for(String au:authors) {
    		tmp.add(au);
    	}
        return tmp;
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return this.year;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    public String toString() {
        String res = "";
        res += this.title;
        for(String au:authors) {
        	res += (", " + au);
        }
        res += (", "  + Integer.toString(year));
        return res;
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
     @Override
     public boolean equals(Object that) {
         Book thatbook = (Book)that;
         if(!title.equals(thatbook.getTitle())) {
        	 return false;
         }
         
         if(year != thatbook.getYear()) {
        	 return false;
         }
         
         List<String> thatauthors = thatbook.getAuthors();
         if(thatauthors.size() != authors.size()) {
        	 return false;
         }
         for(String au:authors) {
        	 if(!thatauthors.contains(au)) {
        		 return false;
        	 }
         }
         return true;
     }
     
     @Override
     public int hashCode() {
         int res = 0;
         res += title.hashCode();
         res += Integer.hashCode(year);
         for(String au:authors) {
        	 res += au.hashCode();
         }
         return res;
     }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
