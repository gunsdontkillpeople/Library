package controllers;

import com.avaje.ebean.Model;
import models.book.Book;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class LibraryController extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result booksPage() {
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        return ok(books_page.render(books));
    }

    public Result instancesPage() {
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        int i = 0;
        return ok(instances_page.render(books, i));
    }

    public Result usersPage() {
        return ok(users_page.render());
    }

    public Result deliveryPointsPage() {
        return ok(delivery_points_page.render());
    }

    public Result transfersPage() {
        return ok(transfers_page.render());
    }

    public Result addBook() {

        Book book = Form.form(Book.class).bindFromRequest().get();
        book.save();

        return redirect(routes.LibraryController.booksPage());
    }

}
