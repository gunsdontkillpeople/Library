package controllers;

import models.book.Book;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class LibraryController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result addBooks() {

        Book book = Form.form(Book.class).bindFromRequest().get();
        book.save();

        return redirect(routes.HomeController.index());
    }

}
