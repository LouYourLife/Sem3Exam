package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int isbn;
    private String title;
    private String publisher;
    private int publishYear;
    
    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private List<Author> authors;
    
    @ManyToOne
    private Library library;

    public Book() {
    }

    public Book(int isbn, String title, String publisher, int publishYear) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.authors = new ArrayList<>();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
    public void addAuthor(Author author) {
        if (author != null) {
            this.authors.add(author);
            author.getBooks().add(this);
        }
    }
    
    public void removeAuthor(Author author) {
        if (author != null) {
            authors.remove(author);
            author.getBooks().remove(this);
        }
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

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) isbn;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if (this.isbn != other.isbn) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Book[ isbn=" + isbn + " ]";
    }
    
}
