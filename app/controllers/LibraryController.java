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
    private Book book = null;

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result booksPage() {
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        return ok(books_page.render(books));
    }

    public Result instancesPage() {
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        return ok(instances_page.render(books));
    }

    public Result usersPage() {
        return ok(users_page.render());
    }

    public Result deliveryPointsPage() {
        List<DeliveryPoint> points = new Model.Finder(String.class, DeliveryPoint.class).all();
        return ok(delivery_points_page.render(points, deliveryPointType));
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
        return redirect(routes.LibraryController.index());
    }

    public Result selectBook() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        book = (Book) new Model.Finder(String.class, Book.class).byId(data.get("Books"));
        return redirect(routes.LibraryController.instancesPage());
    }

}
