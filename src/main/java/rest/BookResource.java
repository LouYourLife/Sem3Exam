package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.BookDTO;
import facades.BookFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("book")
public class BookResource {
    private final BookFacade facade = new BookFacade();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("title/{title}")
    //@RolesAllowed("user")
    public String searchBooksByTitle(@PathParam("title") String title) {
        List<BookDTO> list = BookFacade.getBooksByTitle(title);
        return GSON.toJson(list);
    }
}
