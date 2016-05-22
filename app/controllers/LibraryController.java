package controllers;

import com.avaje.ebean.Model;
import models.book.Book;
import models.book.BookInstance;
import models.deliverypoint.DeliveryPoint;
import models.deliverypoint.DeliveryPointType;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Pair;
import views.html.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class LibraryController extends Controller {

    private Integer deliveryPointType = null;
    private Long selectedBookId = null;
    private Long selectedDeliveryPointId = null;


    public Result index() {
        Book book = null;
        DeliveryPoint deliveryPoint = null;
        if(selectedBookId != null)
            book = (Book) new Model.Finder(String.class, Book.class).byId(selectedBookId);
        if(selectedDeliveryPointId != null)
            deliveryPoint = (DeliveryPoint) new Model.Finder(String.class, DeliveryPoint.class).byId(selectedDeliveryPointId);
        String bookString = (book == null) ? "Not selected" : book.toString();
        String deliveryPointString = (deliveryPoint == null) ? "Not selected" : deliveryPoint.toString();
        return ok(index.render(bookString, deliveryPointString));
    }

    public Result booksPage() {
        List<Book> booksList = new Model.Finder(String.class, Book.class).all();
        return ok(books.render(booksList));
    }

    public Result instancesPage() {
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        List<Pair<Book, Long>> booksByDeliveryPoint = BookInstance.instancesByDeliveryPointId(selectedDeliveryPointId);
        return ok(instances.render(books, selectedBookId, selectedDeliveryPointId, booksByDeliveryPoint));
    }

    public Result usersPage() {
        return ok(users.render());
    }

    public Result deliveryPointsPage() {
        List<DeliveryPoint> points = new Model.Finder(String.class, DeliveryPoint.class).all();
        return ok(deliveryPoints.render(points, deliveryPointType, selectedDeliveryPointId));
    }

    public Result transfersPage() {
        return ok(transfers.render());
    }

    public Result addBook() {

        Book book = Form.form(Book.class).bindFromRequest().get();
        book.save();

        return redirect(routes.LibraryController.booksPage());
    }

    public Result addInstance() {

        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();

        Integer amount = Integer.decode(data.get("amount"));

        String dateString = data.get("date");
        Date date = parseDateFromString(dateString);

        Book book = (Book) new Model.Finder(String.class, Book.class).byId(selectedBookId);
        DeliveryPoint deliveryPoint = (DeliveryPoint) new Model.Finder(String.class, DeliveryPoint.class).byId(selectedDeliveryPointId);

        for(int i = 0; i < amount; i++) {
            BookInstance bookInstance = new BookInstance();
            bookInstance.date = date;
            bookInstance.book = book;
            bookInstance.deliveryPoint = deliveryPoint;
            bookInstance.save();
        }

        return redirect(routes.LibraryController.instancesPage());
    }

    public Result addDeliveryPoint() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        DeliveryPointType t;
        if(deliveryPointType == 0){
            t = DeliveryPointType.READING_ROOM;
        }else{
            t = DeliveryPointType.DELIVERY_DESK;
        }
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.name = data.get("name");
        deliveryPoint.address = data.get("address");
        deliveryPoint.deliveryPointType = t;
        deliveryPoint.save();
        return redirect(routes.LibraryController.deliveryPointsPage());
    }

    public Result addDeliveryPointType() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        deliveryPointType = Integer.decode(data.get("Delivery Points"));
        return redirect(routes.LibraryController.deliveryPointsPage());
    }

    public Result selectDeliveryPoint() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedDeliveryPointId = Long.decode(data.get("Delivery Points"));
        return redirect(routes.LibraryController.deliveryPointsPage());
    }

    public Result selectBook() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedBookId = ((Book) new Model.Finder(String.class, Book.class).byId(data.get("Books"))).id;
        return redirect(routes.LibraryController.instancesPage());
    }

    private Date parseDateFromString(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {

            date = formatter.parse(str);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
