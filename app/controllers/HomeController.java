package controllers;

import com.avaje.ebean.Model;
import models.book.Book;
import play.mvc.*;

import views.html.*;

import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    public Result showBooksPage() {
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        return ok(show_books.render(books));
    }

    public Result addBooksPage() {
        return ok(add_books.render());
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
