package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookDTO;
import dto.LoanDTO;
import entities.Loan;
import facades.BookFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

@Path("book")
public class BookResource {
    private final BookFacade facade = new BookFacade();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public String getAllBooks() {
        List<String> list = facade.getAllBooks();
        return GSON.toJson(list);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("title/{title}")
    @RolesAllowed("user")
    public String searchBooksByTitle(@PathParam("title") String title) {
        List<BookDTO> list = facade.getBooksByTitle(title);
        return GSON.toJson(list);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("loan")
    @RolesAllowed("user")
    public String addLoan(String loan) {
        LoanDTO l = GSON.fromJson(loan, LoanDTO.class);
        LoanDTO lDTO = facade.makeLoan(l);
        //LoanDTO lDTO = facade.makeLoan(username, password, isbn);
        String json = GSON.toJson(lDTO);
        return json;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String addBook(String book) {
        BookDTO bDTO = GSON.fromJson(book, BookDTO.class);
        BookDTO bDTO2 = facade.addBook(bDTO);
        String json = GSON.toJson(bDTO2);
        return json;
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{isbn}")
    @RolesAllowed("admin")
    public String deleteBook(@PathParam("isbn") int isbn) {
        BookDTO bDTO = facade.deleteBook(isbn);
        String json = GSON.toJson(bDTO);
        return json;
    }
}
