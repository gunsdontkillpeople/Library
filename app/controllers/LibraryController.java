package controllers;

import com.avaje.ebean.Model;
import models.book.Book;
import models.deliverypoint.DeliveryPoint;
import models.deliverypoint.DeliveryPointType;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.List;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class LibraryController extends Controller {

    private int deliveryPointType = 0;
    private long selectedBookId = 0;
    private long selectedDeliveryPointId = 0;

    public Result index() {
        Book book = (Book) new Model.Finder(String.class, Book.class).byId(selectedBookId);
        DeliveryPoint point = (DeliveryPoint) new Model.Finder(String.class, DeliveryPoint.class).byId(selectedDeliveryPointId);
        String bookString = (book == null) ? "Not selected" : book.toString();
        String deliveryPointString = (point == null) ? "Not selected" : point.toString();
        return ok(index.render(bookString, deliveryPointString));
    }

    public Result booksPage() {
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        return ok(books_page.render(books));
    }

    public Result instancesPage() {
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        return ok(instances_page.render(books, selectedBookId));
    }

    public Result usersPage() {
        return ok(users_page.render());
    }

    public Result deliveryPointsPage() {
        List<DeliveryPoint> points = new Model.Finder(String.class, DeliveryPoint.class).all();
        return ok(delivery_points_page.render(points, deliveryPointType, selectedDeliveryPointId));
    }

    public Result transfersPage() {
        return ok(transfers_page.render());
    }

    public Result addBook() {

        Book book = Form.form(Book.class).bindFromRequest().get();
        book.save();

        return redirect(routes.LibraryController.booksPage());
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
        selectedDeliveryPointId = Integer.decode(data.get("Delivery Points"));
        return redirect(routes.LibraryController.deliveryPointsPage());
    }

    public Result selectBook() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedBookId = ((Book) new Model.Finder(String.class, Book.class).byId(data.get("Books"))).id;
        return redirect(routes.LibraryController.instancesPage());
    }

}
