package controllers;

import com.avaje.ebean.Model;
import models.book.Book;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;

/**
 * Created by mistler on 20.05.16.
 */
public class BooksController extends Controller {

    public Result addBooks() {

        Book book = Form.form(Book.class).bindFromRequest().get();
        book.save();

        return redirect(routes.HomeController.index());
    }

    public Result listBooks() {

        List<Book> books = new Model.Finder(String.class, Book.class).all();

        return ok(toJson(books));
    }
}
