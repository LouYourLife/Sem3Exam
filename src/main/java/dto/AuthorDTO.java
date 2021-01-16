package dto;

import entities.Author;
import entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthorDTO {
    private String name;
    //private List<Book> books;

    public AuthorDTO() {
    }

    public AuthorDTO(Author author) {
        this.name = author.getName();
//        this.books = new ArrayList<>();
//        for (Book book : author.getBooks()) {
//            this.books.add(new BookDTO(book));
//        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    @Override
    public String toString() {
        return "AuthorDTO{" + "name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.name);
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
        final AuthorDTO other = (AuthorDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
}
