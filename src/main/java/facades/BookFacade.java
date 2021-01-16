package facades;

import dto.BookDTO;
import entities.Book;
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
    
    public static List<BookDTO> getBooksByTitle(String title) {
        //EntityManager em = emf.createEntityManager();
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
        
        //return list;
    }
    
//    public static void main(String[] args) {
//        List<BookDTO> list = getBooksByTitle("Coraline");
//        System.out.println("Main");
//        System.out.println(list);
//    }
}
