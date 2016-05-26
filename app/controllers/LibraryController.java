package controllers;

import com.avaje.ebean.Model;
import models.book.Book;
import models.book.BookInstance;
import models.book.TakenBook;
import models.deliverypoint.BookTransfer;
import models.deliverypoint.DeliveryPoint;
import models.deliverypoint.DeliveryPointType;
import models.user.LibraryUser;
import models.user.ProfessorUserCategoryCharacteristic;
import models.user.StudentUserCategoryCharacteristic;
import models.user.UserCategory;
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
public class LibraryController extends Controller {

    private DeliveryPointType selectedDeliveryPointType = null;
    private DeliveryPoint selectedDeliveryPoint = null, selectedDeliveryPointSrc = null;
    private Book selectedBook = null;
    private UserCategory selectedUserCategory = null;
    private LibraryUser selectedLibraryUser = null;

    public LibraryController(){
        if(new Model.Finder(String.class, DeliveryPointType.class).all().isEmpty()) {
            Assistant.initDB();
        }
        selectedDeliveryPointType = (DeliveryPointType) new Model.Finder(String.class, DeliveryPointType.class).all().get(0);
        selectedUserCategory = (UserCategory) new Model.Finder(String.class, UserCategory.class).all().get(0);
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
        List<Pair<DeliveryPoint, Long>> deliveryPointAmount = null;
        if(selectedBook != null){
            deliveryPointAmount = BookInstance.byBook(selectedBook);
        }
        return ok(instances.render(books, selectedBook, selectedDeliveryPoint, deliveryPointAmount));
    }

    public Result addUsersPage() {
        List<UserCategory> userCategories = new Model.Finder<>(String.class, UserCategory.class).all();
        List<LibraryUser> libraryUsers = new Model.Finder<>(String.class, LibraryUser.class).all();
        return ok(users.render(userCategories, selectedUserCategory, libraryUsers, selectedLibraryUser));
    }

    public Result workWithUserPage() {
        List<TakenBook> takenBooks = null;
        if(selectedLibraryUser != null){
            takenBooks = TakenBook.byUserCurrently(selectedLibraryUser);
        }
        List<Pair<Book, Long>> bookInstances = null;
        if(selectedDeliveryPoint != null){
            bookInstances = BookInstance.byDeliveryPoint(selectedDeliveryPoint);
        }
        List<BookTransfer> transfers = null;
        if(selectedLibraryUser != null){
            transfers = BookTransfer.byUser(selectedLibraryUser);
        }
        return ok(workWithUser.render(selectedLibraryUser, takenBooks, bookInstances, transfers, selectedDeliveryPoint));
    }

    public Result userFinesPage() {
        return play.mvc.Results.TODO;
    }

    public Result deliveryPointsPage() {
        List<DeliveryPoint> points = new Model.Finder(String.class, DeliveryPoint.class).all();
        List<DeliveryPointType> pointTypes = new Model.Finder(String.class, DeliveryPointType.class).all();
        return ok(deliveryPoints.render(pointTypes, points, selectedDeliveryPointType, selectedDeliveryPoint));
    }

    public Result deliveryPointBooksPage() {
        List<Pair<Book, Long>> booksByDeliveryPoint = null;
        if(selectedDeliveryPoint != null){
            booksByDeliveryPoint = BookInstance.byDeliveryPoint(selectedDeliveryPoint);
        }
        return ok(deliveryPointBooks.render(selectedDeliveryPoint, booksByDeliveryPoint));
    }

    public Result transfersPage() {
        List<DeliveryPoint> points = new Model.Finder(String.class, DeliveryPoint.class).all();
        List<Pair<Book, Long>> booksByDeliveryPoint = null;
        if(selectedDeliveryPointSrc != null){
            booksByDeliveryPoint = BookInstance.byDeliveryPoint(selectedDeliveryPointSrc);
        }
        return ok(transfers.render(points, selectedDeliveryPointSrc, selectedDeliveryPoint, booksByDeliveryPoint, selectedBook, selectedLibraryUser));
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
        Date date = Assistant.parseDateFromString(dateString);

        for(int i = 0; i < amount; i++) {
            BookInstance bookInstance = new BookInstance();
            bookInstance.date = date;
            bookInstance.book = selectedBook;
            bookInstance.deliveryPoint = selectedDeliveryPoint;
            bookInstance.bookInstanceStatus = "Delivery point";
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
        if(selectedUserCategory.name == "Student"){
            StudentUserCategoryCharacteristic characteristic = new StudentUserCategoryCharacteristic(user, data.get("faculty"), data.get("group"));
            characteristic.save();
        }
        if(selectedUserCategory.name == "Professor"){
            ProfessorUserCategoryCharacteristic characteristic = new ProfessorUserCategoryCharacteristic(user, data.get("chair"), data.get("rank"), data.get("degree"));
            characteristic.save();
        }
        return redirect(routes.LibraryController.addUsersPage());
    }

    public Result makeTransfer() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedBook = (Book) new Model.Finder(String.class, Book.class).byId(data.get("Books"));
        List<BookInstance> bookInstances = BookInstance.byDeliveryPointAndBook(selectedDeliveryPointSrc, selectedBook);
        if(!bookInstances.isEmpty()){
            BookInstance instance = bookInstances.get(0);
            instance.deliveryPoint = null;
            instance.date = null;
            instance.bookInstanceStatus = "Transfer";
            BookTransfer transfer = new BookTransfer(instance, selectedLibraryUser, selectedDeliveryPointSrc, selectedDeliveryPoint, Assistant.today(), Assistant.nextDay(10));
            instance.update();
            transfer.save();
        }
        return redirect(routes.LibraryController.transfersPage());
    }

    public Result returnBook() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        TakenBook takenBook = (TakenBook) new Model.Finder(String.class, TakenBook.class).byId(data.get("TakenBooks"));
        BookInstance bookInstance = takenBook.bookInstance;
        takenBook.takenBookStatus = "Returned";
        takenBook.returnDate = Assistant.today();
        bookInstance.bookInstanceStatus = "Delivery point";
        takenBook.update();
        bookInstance.update();
        return redirect(routes.LibraryController.workWithUserPage());
    }

    public Result takeBook() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Book book = (Book) new Model.Finder(String.class, Book.class).byId(data.get("TakeBook"));
        List<BookInstance> bookInstances = BookInstance.byDeliveryPointAndBook(selectedDeliveryPoint, book);
        if(!bookInstances.isEmpty()){
            BookInstance instance = bookInstances.get(0);
            instance.bookInstanceStatus = "User";
            TakenBook takenBook = new TakenBook(selectedLibraryUser, "User", instance, Assistant.today(), Assistant.nextDay(30));
            instance.update();
            takenBook.save();
        }
        return redirect(routes.LibraryController.workWithUserPage());
    }

    public Result takeBookFromTransfer() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        BookTransfer bookTransfer = (BookTransfer) new Model.Finder(String.class, BookTransfer.class).byId(data.get("TakeBook"));
        BookInstance instance = bookTransfer.bookInstance;
        instance.bookInstanceStatus = "User";
        instance.deliveryPoint = selectedDeliveryPoint;
        instance.date = Assistant.today();
        TakenBook takenBook = new TakenBook(selectedLibraryUser, "User", instance, Assistant.today(), Assistant.nextDay(30));
        instance.update();
        takenBook.save();
        bookTransfer.delete();
        return redirect(routes.LibraryController.workWithUserPage());
    }

    public Result resolveFine() {
        return play.mvc.Results.TODO;
    }

    public Result selectDeliveryPoint() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedDeliveryPoint = (DeliveryPoint) new Model.Finder(String.class, DeliveryPoint.class).byId(data.get("Delivery Points"));
        return redirect(routes.LibraryController.deliveryPointsPage());
    }

    public Result selectDeliveryPointSrc() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedDeliveryPointSrc = (DeliveryPoint) new Model.Finder(String.class, DeliveryPoint.class).byId(data.get("Delivery Points"));
        return redirect(routes.LibraryController.transfersPage());
    }

    public Result selectDeliveryPointDst() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedDeliveryPoint = (DeliveryPoint) new Model.Finder(String.class, DeliveryPoint.class).byId(data.get("Delivery Points"));
        return redirect(routes.LibraryController.transfersPage());
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
        return redirect(routes.LibraryController.addUsersPage());
    }

    public Result selectUser() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        selectedLibraryUser = (LibraryUser) new Model.Finder(String.class, LibraryUser.class).byId(data.get("Users"));
        return redirect(routes.LibraryController.addUsersPage());
    }
}
