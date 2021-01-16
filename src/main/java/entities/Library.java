package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Library implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    
    @OneToMany(mappedBy = "library", cascade = CascadeType.PERSIST)
    private List<Book> ownedBooks;

    public Library() {
    }

    public Library(String name) {
        this.name = name;
        this.ownedBooks = new ArrayList<>();
    }

    public List<Book> getOwnedBooks() {
        return ownedBooks;
    }
    
    public void addBook(Book book) {
        this.ownedBooks.add(book);
        if(book != null) {
            book.setLibrary(this);
        }
    }
    
    public void removeBook(Book book) {
        if(book != null) {
            ownedBooks.remove(book);
        }
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Library)) {
            return false;
        }
        Library other = (Library) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Library[ Name=" + name + " ]";
    }
    
}
