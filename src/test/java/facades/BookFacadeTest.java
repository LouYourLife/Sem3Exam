package facades;

import dto.BookDTO;
import entities.Author;
import entities.Book;
import entities.Library;
import entities.Loan;
import entities.Member;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

public class BookFacadeTest {
    private static EntityManagerFactory emf;
    private static BookFacade facade;
    
    private Library l1;
    private Author a1;
    private Author a2;
    private Author a3;
    private Book b1;
    private Book b2;
    private Book b3;
    private Book b4;
    private Member m1;
    private Loan loan;
    
    public BookFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = BookFacade.getBookFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        
        try {
            l1 = new Library("Slangerup Bibliotek");
            a1 = new Author("Neil Gaiman");
            a2 = new Author("Terry Pratchett");
            a3 = new Author("Anton LaVey");
            b1 = new Book(1234, "Good Omens", "Pub1", 2002);
            b2 = new Book(4321, "Coraline", "Pub2", 2003);
            b3 = new Book(6666, "The Satanic Bible", "Pub3", 1993);
            b4 = new Book(987, "Test5", "Ye", 1853);
            m1 = new Member("Lou", "123");
            loan = new Loan(new Date());
            
            b1.addLoan(loan);
            b3.addLoan(loan);
            m1.AddMemLoans(loan);
            
            l1.addBook(b1);
            l1.addBook(b2);
            l1.addBook(b3);
            l1.addBook(b4);
            b1.addAuthor(a1);
            b1.addAuthor(a2);
            b2.addAuthor(a1);
            b3.addAuthor(a3);
            b4.addAuthor(a1);
            
            em.getTransaction().begin();
            em.persist(l1);
            em.persist(a1);
            em.persist(a2);
            em.persist(a3);
            em.persist(loan);
            em.persist(m1);
            em.persist(b1);
            em.persist(b2);
            em.persist(b3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            b1.removeLoan(loan);
            b3.removeLoan(loan);
            m1.removeMemLoans(loan);
            
            l1.removeBook(b1);
            l1.removeBook(b2);
            l1.removeBook(b3);
            l1.removeBook(b4);
            
            b1.removeAuthor(a1);
            b1.removeAuthor(a2);
            b2.removeAuthor(a1);
            b3.removeAuthor(a3);
            b4.removeAuthor(a1);
            
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Loan").executeUpdate();
            em.createQuery("DELETE FROM Member").executeUpdate();
            em.createQuery("DELETE FROM Author").executeUpdate();
            em.createQuery("DELETE FROM Book").executeUpdate();
            em.createQuery("DELETE FROM Library").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
    }

    /**
     * Test of getBooksByTitle method, of class BookFacade.
     */
    //@Test
    public void testGetBooksByTitle() {
        String title = "coraline";
        String exp = "Coraline";
        List<BookDTO> list = facade.getBooksByTitle(title);
        assertEquals(exp, list.get(0).getTitle());
    }

    /**
     * Test of getAllBooks method, of class BookFacade.
     */
    //@Test
    public void testGetAllBooks() {
        String title = "Coraline";
        List<String> list = facade.getAllBooks();
        assertTrue(list.contains(title));
    }

    /**
     * Test of addBook method, of class BookFacade.
     */
    //@Test
    public void testAddBook() {
//        Book book = new Book(995, "Test2", "Pub5", 2016);
//        Author aut = new Author("Me");
//        book.addAuthor(aut);
//        l1.addBook(book);
        //BookDTO b = new BookDTO(book);
        BookDTO b = new BookDTO(555, "Test1", "Pub4", 2006, "Someone");
        BookDTO bb = new BookDTO(b4);
        BookDTO bDTO = facade.addBook(bb);
        List<String> list = facade.getAllBooks();
        BookDTO b2 = facade.deleteBook(b4.getIsbn());
        assertTrue(list.contains(bDTO.getTitle()));
        //assertEquals(b.getTitle(), bDTO.getTitle());
    }

    /**
     * Test of deleteBook method, of class BookFacade.
     */
    //@Test
    public void testDeleteBook() {
        BookDTO b = facade.deleteBook(b1.getIsbn());
        System.out.println(b);
        List<String> list = facade.getAllBooks();
        assertEquals(list.size(), 2);
//        System.out.println("deleteBook");
//        int isbn = 0;
//        BookFacade instance = new BookFacade();
//        BookDTO expResult = null;
//        BookDTO result = instance.deleteBook(isbn);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
