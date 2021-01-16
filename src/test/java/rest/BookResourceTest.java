package rest;

import dto.BookDTO;
import entities.Author;
import entities.Book;
import entities.Library;
import entities.Loan;
import entities.Member;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static rest.LoginEndpointTest.BASE_URI;
import utils.EMF_Creator;

public class BookResourceTest {
    
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    
    private static Library l1;
    private static Author a1, a2;
    private static Book b1, b2, b3;
    private static Member m1;
    private static Loan loan;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }
    
//    public BookResourceTest() {
//    }
    
    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }
    
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        
        try {
            l1 = new Library("Slangerup Bibliotek");
        a1 = new Author("Neil Gaiman");
        a2 = new Author("Anton LaVey");
        b1 = new Book(765, "Coraline", "Pub1", 2003);
        b2 = new Book(567, "The Satanic Bible", "Pub2", 1996);
        b3 = new Book(432, "Test", "Pub3", 2021);
        m1 = new Member("Lou", "123");
        loan = new Loan(new Date());
        
        b1.addLoan(loan);
        m1.AddMemLoans(loan);
        l1.addBook(b1);
        l1.addBook(b2);
        l1.addBook(b3);
        b1.addAuthor(a1);
        b2.addAuthor(a2);
        b3.addAuthor(a1);
        
        em.getTransaction().begin();
        em.createQuery("Delete from User").executeUpdate();
        em.createQuery("delete from Role").executeUpdate();
        
        Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
        
        em.persist(l1);
        em.persist(a1);
        em.persist(a2);
        em.persist(loan);
        em.persist(m1);
        em.persist(b1);
        em.persist(b2);
        em.getTransaction().commit();
        } finally {
            em.close();
        }
        
//        private static Library l1;
//    private static Author a1, a2;
//    private static Book b1, b2;
//    private static Member m1;
//    private static Loan loan;
    }
    
    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            b1.removeLoan(loan);
            m1.removeMemLoans(loan);
            
            l1.removeBook(b1);
            l1.removeBook(b2);
            l1.removeBook(b3);
            
            b1.removeAuthor(a1);
            b2.removeAuthor(a2);
            b3.removeAuthor(a1);
            
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
    
    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;
    
    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }
    
    //@Test
    public void testServerIsUp() {
        given().when().get("/xxx").then().statusCode(200);
    }

    /**
     * Test of getAllBooks method, of class BookResource.
     */
    @Test
    public void testGetAllBooks() {
//        login("user","test");
//        given()
//                .contentType("application/json")
//                .header("x-access-token", securityToken)
//                .when()
//                .get("/book").then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode());
        login("user","test");
        List<String> list;
        list = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/book").then()
                .extract().body().jsonPath().getList("", String.class);
        String s1 = list.get(0);
        String s2 = list.get(1);
        String s3 = list.get(2);
        assertThat(list, contains(s1, s2, s3));
    }

    /**
     * Test of searchBooksByTitle method, of class BookResource.
     */
    @Test
    public void testSearchBooksByTitle() {
        login("user","test");
                given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/book/title/Coraline").then()
                .body("title", Matchers.contains("Coraline"));
    }

    /**
     * Test of addBook method, of class BookResource.
     */
    //@Test
    public void testAddBook() {
        login("admin","test");
        given()
                .contentType("application/json")
                .body(new BookDTO(b3))
                .header("x-access-token", securityToken)
                .when()
                .post("/book")
                .then()
                .body("title", equalTo(b3.getTitle()));
    }

    /**
     * Test of deleteBook method, of class BookResource.
     */
    //@Test
    public void testDeleteBook() {
        System.out.println("deleteBook");
        int isbn = 0;
        BookResource instance = new BookResource();
        String expResult = "";
        String result = instance.deleteBook(isbn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
