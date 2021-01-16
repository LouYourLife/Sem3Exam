package dto;

import entities.Author;
import entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookDTO {
    private int isbn;
    private String title;
    private String publisher;
    private int publishYear;
    private List<AuthorDTO> authors;

    public BookDTO() {
    }
    
    public BookDTO(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.publisher = book.getPublisher();
        this.publishYear = book.getPublishYear();
        
        this.authors = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            this.authors.add(new AuthorDTO(author));
        }
    }
    
    public BookDTO(int isbn, String title, String publisher, int pYear, String author) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publishYear = pYear;
        
        //Idea, separate string with kommas and add all to arraylist if time
        this.authors = new ArrayList<>();
        Author a = new Author(author);
        AuthorDTO aDTO = new AuthorDTO(a);
        this.authors.add(aDTO);
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "BookDTO{" + "isbn=" + isbn + ", title=" + title + ", publisher=" + publisher + ", publishYear=" + publishYear + ", authors=" + authors + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.isbn;
        hash = 79 * hash + Objects.hashCode(this.title);
        hash = 79 * hash + Objects.hashCode(this.publisher);
        hash = 79 * hash + this.publishYear;
        hash = 79 * hash + Objects.hashCode(this.authors);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BookDTO other = (BookDTO) obj;
        if (this.isbn != other.isbn) {
            return false;
        }
        if (this.publishYear != other.publishYear) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.publisher, other.publisher)) {
            return false;
        }
        if (!Objects.equals(this.authors, other.authors)) {
            return false;
        }
        return true;
    }
    
    
}
