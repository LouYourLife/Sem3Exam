package facades;

import dto.AuthorDTO;
import dto.BookDTO;
import entities.Author;
import entities.Book;
import entities.Library;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

public class BookFacade {
    
    private static BookFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    //private BookFacade() {}
    
    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }
    
    public List<BookDTO> getBooksByTitle(String title) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        List<BookDTO> list = new ArrayList<>();
        
        try {
            TypedQuery<Book> tq = em.createQuery("SELECT b FROM Book b WHERE b.title='" + title + "'", Book.class);
            
            List<Book> books = tq.getResultList();
            if(books.isEmpty()) {
                System.out.println("Shoot! Empty!");
            }
            System.out.println("Books:");
            System.out.println(books);
            
            for(Book book : books) {
                BookDTO bDTO = new BookDTO(book);
                list.add(bDTO);
            }
            System.out.println("BookDTOs:");
            System.out.println(list);
            return list;
        } finally {
            em.close();
        }

    }
    
    public List<String> getAllBooks() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        try {
            TypedQuery tq = em.createQuery("SELECT b.title FROM Book AS b", String.class);
            List<String> list = tq.getResultList();
            return list;
        } finally {
            em.close();
        }
    }
    
    public BookDTO addBook(BookDTO b) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        Book book = new Book(b.getIsbn(), b.getTitle(), b.getPublisher(), b.getPublishYear());
        BookDTO bDTO;
        
        try {
            Library l = em.find(Library.class, "Gladsaxe Bibliotek");
            List<AuthorDTO> aDTOs = b.getAuthors();
            for(AuthorDTO a : aDTOs) {
                Author au = new Author(a.getName());
                book.addAuthor(au);
            }
            l.addBook(book);
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            bDTO = new BookDTO(book);
            return bDTO;
        } finally {
            em.close();
        }
    }
    
    public BookDTO deleteBook(int isbn) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        Book book = em.find(Book.class, isbn);
        if(book == null) {
            //Throw Exception??
        } else {
            try {
                em.getTransaction().begin();
//                List<Author> authors = book.getAuthors();
//                for(Author a : authors) {
//                    em.remove(a);
//                }
                em.remove(book);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return new BookDTO(book);
    }
    
    
    
//    public static void main(String[] args) {
//        //List<BookDTO> list = getBooksByTitle("Coraline");
//        //List<BookDTO> list = getAllBooks();
//        BookDTO b = new BookDTO(43251, "Test2", "BookMania", 2004, "Thomas Mortem");
//        //BookDTO bDTO = addBook(b);
//        BookDTO bDTO = deleteBook(11446);
//        System.out.println("Main");
//        System.out.println(bDTO);
//    }
}
