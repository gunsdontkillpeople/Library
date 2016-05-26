package controllers;

import com.avaje.ebean.Model;
import models.book.Book;
import models.book.BookInstance;
import models.book.TakenBook;
import models.deliverypoint.BookTransfer;
import models.deliverypoint.DeliveryPoint;
import models.deliverypoint.DeliveryPointType;
import models.user.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Assistant;
import utils.Pair;
import views.html.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class BookController extends Controller {

    public Result booksPage() {
        List<Book> booksList = Book.all();
        return ok(books.render(booksList));
    }

    public Result instancesPage() {
        List<Book> books = Book.all();
        List<Pair<DeliveryPoint, Long>> deliveryPointAmount = null;
        if(Assistant.selectedBook != null){
            deliveryPointAmount = BookInstance.byBook(Assistant.selectedBook);
        }
        return ok(instances.render(books, Assistant.selectedBook, Assistant.selectedDeliveryPoint, deliveryPointAmount));
    }

    public Result transfersPage() {
        List<DeliveryPoint> points = DeliveryPoint.all();
        List<Pair<Book, Long>> booksByDeliveryPoint = null;
        if(Assistant.selectedDeliveryPointSrc != null){
            booksByDeliveryPoint = BookInstance.byDeliveryPoint(Assistant.selectedDeliveryPointSrc);
        }
        return ok(transfers.render(points, Assistant.selectedDeliveryPointSrc, Assistant.selectedDeliveryPoint, booksByDeliveryPoint, Assistant.selectedBook, Assistant.selectedLibraryUser));
    }

    public Result addBook() {

        Book book = Form.form(Book.class).bindFromRequest().get();
        book.save();

        return redirect(routes.BookController.booksPage());
    }

    public Result addInstance() {

        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();

        Integer amount = Integer.decode(data.get("amount"));

        String dateString = data.get("date");
        Date date = Assistant.parseDateFromString(dateString);

        for(int i = 0; i < amount; i++) {
            BookInstance bookInstance = new BookInstance();
            bookInstance.date = date;
            bookInstance.book = Assistant.selectedBook;
            bookInstance.deliveryPoint = Assistant.selectedDeliveryPoint;
            bookInstance.bookInstanceStatus = "Delivery point";
            bookInstance.save();
        }

        return redirect(routes.BookController.instancesPage());
    }

    public Result makeTransfer() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Assistant.selectedBook = Book.byId(data.get("Books"));
        List<BookInstance> bookInstances = BookInstance.byDeliveryPointAndBook(Assistant.selectedDeliveryPointSrc, Assistant.selectedBook);
        if(!bookInstances.isEmpty()){
            BookInstance instance = bookInstances.get(0);
            instance.deliveryPoint = null;
            instance.date = null;
            instance.bookInstanceStatus = "Transfer";
            BookTransfer transfer = new BookTransfer(instance, Assistant.selectedLibraryUser, Assistant.selectedDeliveryPointSrc, Assistant.selectedDeliveryPoint, Assistant.today(), Assistant.nextDay(10));
            instance.update();
            transfer.save();
        }
        return redirect(routes.BookController.transfersPage());
    }

    public Result selectBook() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Assistant.selectedBook = Book.byId(data.get("Books"));
        return redirect(routes.BookController.instancesPage());
    }
}
