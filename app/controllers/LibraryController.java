package controllers;

import com.avaje.ebean.Model;
import models.book.Book;
import models.book.BookInstance;
import models.deliverypoint.DeliveryPoint;
import models.deliverypoint.DeliveryPointType;
import models.user.LibraryUser;
import models.user.UserCategory;
import play.data.DynamicForm;
import play.data.Form;
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

    private DeliveryPointType selectedDeliveryPointType = null;
    private DeliveryPoint selectedDeliveryPoint = null;
    private Book selectedBook = null;
    private UserCategory selectedUserCategory = null;

    public LibraryController(){
        if(new Model.Finder(String.class, DeliveryPointType.class).all().isEmpty()) {
            initLibraryUserCategories();
            initDeliveryPointTypes();
        }
        selectedDeliveryPointType = (DeliveryPointType) new Model.Finder(String.class, DeliveryPointType.class).all().get(0);
        selectedUserCategory = (UserCategory) new Model.Finder(String.class, UserCategory.class).all().get(0);
    }

    private void initDeliveryPointTypes() {
        new DeliveryPointType("Delivery desk").save();
        new DeliveryPointType("Reading room").save();
    }

    private void initLibraryUserCategories() {
        new UserCategory("Student").save();
        new UserCategory("Professor").save();
        new UserCategory("Enrollee").save();
        new UserCategory("Trainee").save();
        new UserCategory("TF listener").save();
    }

    public Result index() {
        String bookString = (selectedBook == null) ? "Not selected" : selectedBook.toString();
        String deliveryPointString = (selectedDeliveryPoint == null) ? "Not selected" : selectedDeliveryPoint.toString();
        return ok(index.render(bookString, deliveryPointString));
    }

    public Result booksPage() {
        List<Book> booksList = new Model.Finder(String.class, Book.class).all();
        return ok(books.render(booksList));
    }

    public Result instancesPage() {
        List<Book> books = new Model.Finder(String.class, Book.class).all();
        List<Pair<Book, Long>> booksByDeliveryPoint = null;
        if(selectedDeliveryPoint != null){
            booksByDeliveryPoint = BookInstance.instancesByDeliveryPointId(selectedDeliveryPoint.id);
        }
        return ok(instances.render(books, selectedBook, selectedDeliveryPoint, booksByDeliveryPoint));
    }

    public Result usersPage() {
        List<UserCategory> userCategories = new Model.Finder<>(String.class, UserCategory.class).all();
        return ok(users.render(userCategories, selectedUserCategory));
    }

    public Result deliveryPointsPage() {
        List<DeliveryPoint> points = new Model.Finder(String.class, DeliveryPoint.class).all();
        List<DeliveryPointType> pointTypes = new Model.Finder(String.class, DeliveryPointType.class).all();
        return ok(deliveryPoints.render(pointTypes, points, selectedDeliveryPointType, selectedDeliveryPoint));
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

        for(int i = 0; i < amount; i++) {
            BookInstance bookInstance = new BookInstance();
            bookInstance.date = date;
            bookInstance.book = selectedBook;
            bookInstance.deliveryPoint = selectedDeliveryPoint;
            bookInstance.save();
        }

        return redirect(routes.LibraryController.instancesPage());
    }

    public Result addDeliveryPoint() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.name = data.get("name");
        deliveryPoint.address = data.get("address");
        deliveryPoint.deliveryPointType = selectedDeliveryPointType;
        deliveryPoint.save();
        return redirect(routes.LibraryController.deliveryPointsPage());
    }

    public Result addUser() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        LibraryUser user = new LibraryUser(selectedUserCategory, data.get("name"), data.get("middlename"), data.get("surname"));
        user.save();
        return redirect(routes.LibraryController.usersPage());
    }

    public Result selectDeliveryPoint() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedDeliveryPoint = (DeliveryPoint) new Model.Finder(String.class, DeliveryPoint.class).byId(data.get("Delivery Points"));
        return redirect(routes.LibraryController.deliveryPointsPage());
    }

    public Result selectDeliveryPointType() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedDeliveryPointType = (DeliveryPointType) new Model.Finder(String.class, DeliveryPointType.class).byId(data.get("Delivery Points"));
        return redirect(routes.LibraryController.deliveryPointsPage());
    }

    public Result selectBook() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedBook = (Book) new Model.Finder(String.class, Book.class).byId(data.get("Books"));
        return redirect(routes.LibraryController.instancesPage());
    }

    public Result selectUserCategory() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedUserCategory = (UserCategory) new Model.Finder(String.class, UserCategory.class).byId(data.get("UserCategory"));
        return redirect(routes.LibraryController.usersPage());
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
