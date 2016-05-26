package controllers;

import com.avaje.ebean.Model;
import models.book.Book;
import models.book.BookInstance;
import models.book.TakenBook;
import models.deliverypoint.BookTransfer;
import models.user.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Assistant;
import utils.Pair;
import views.html.addUser;
import views.html.userFines;
import views.html.workWithUser;

import java.util.List;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UserController extends Controller {

    public Result addUserPage() {
        List<UserCategory> userCategories = UserCategory.all();
        List<LibraryUser> libraryUsers = LibraryUser.all();
        return ok(addUser.render(userCategories, Assistant.selectedUserCategory, libraryUsers, Assistant.selectedLibraryUser));
    }

    public Result workWithUserPage() {
        List<TakenBook> takenBooks = null;
        if(Assistant.selectedLibraryUser != null){
            takenBooks = TakenBook.byUserCurrently(Assistant.selectedLibraryUser);
        }
        List<Pair<Book, Long>> bookInstances = null;
        if(Assistant.selectedDeliveryPoint != null){
            bookInstances = BookInstance.byDeliveryPoint(Assistant.selectedDeliveryPoint);
        }
        List<BookTransfer> transfers = null;
        if(Assistant.selectedLibraryUser != null){
            transfers = BookTransfer.byUser(Assistant.selectedLibraryUser);
        }
        List<LibraryUser> libraryUsers = LibraryUser.all();
        String fineEndDate = UserFine.endDateByUser(Assistant.selectedLibraryUser);
        return ok(workWithUser.render(Assistant.selectedLibraryUser, libraryUsers, takenBooks, bookInstances, transfers, Assistant.selectedDeliveryPoint, fineEndDate));
    }

    public Result userFinesPage() {
        List<UserFine> fines = null;
        if(Assistant.selectedLibraryUser != null){
            fines = UserFine.byUser(Assistant.selectedLibraryUser);
        }
        return ok(userFines.render(Assistant.selectedLibraryUser, fines));
    }

    public Result addUser() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        LibraryUser user = new LibraryUser(Assistant.selectedUserCategory, data.get("name"), data.get("middlename"), data.get("surname"));
        user.save();
        if(Assistant.selectedUserCategory.name == "Student"){
            StudentUserCategoryCharacteristic characteristic = new StudentUserCategoryCharacteristic(user, data.get("faculty"), data.get("group"));
            characteristic.save();
        }
        if(Assistant.selectedUserCategory.name == "Professor"){
            ProfessorUserCategoryCharacteristic characteristic = new ProfessorUserCategoryCharacteristic(user, data.get("chair"), data.get("rank"), data.get("degree"));
            characteristic.save();
        }
        return redirect(routes.UserController.addUserPage());
    }

    public Result resolveFine() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        UserFine fine = UserFine.byId(data.get("Fines"));
        fine.delete();
        return redirect(routes.UserController.userFinesPage());
    }

    public Result selectUserCategory() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Assistant.selectedUserCategory = (UserCategory) new Model.Finder(String.class, UserCategory.class).byId(data.get("UserCategory"));
        return redirect(routes.UserController.addUserPage());
    }

    public Result selectUser() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Assistant.selectedLibraryUser = (LibraryUser) new Model.Finder(String.class, LibraryUser.class).byId(data.get("Users"));
        return redirect(routes.UserController.workWithUserPage());
    }

    public Result returnBook() {
        return returnBookHelper(true);
    }

    public Result lostBook() {
        return returnBookHelper(false);
    }

    private Result returnBookHelper(boolean isReturned){
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        TakenBook takenBook = TakenBook.byId(data.get("TakenBooks"));
        BookInstance bookInstance = takenBook.bookInstance;
        if(isReturned) {
            takenBook.takenBookStatus = "Returned";
            bookInstance.bookInstanceStatus = "Delivery point";
        }else{
            takenBook.takenBookStatus = "Lost";
            bookInstance.bookInstanceStatus = "Lost";
            bookInstance.deliveryPoint = null;
        }
        bookInstance.update();
        takenBook.update();
        return redirect(routes.UserController.workWithUserPage());
    }

    public Result takeBook() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Book book = Book.byId(data.get("TakeBook"));
        List<BookInstance> bookInstances = BookInstance.byDeliveryPointAndBook(Assistant.selectedDeliveryPoint, book);
        if(!bookInstances.isEmpty()){
            BookInstance instance = bookInstances.get(0);
            instance.bookInstanceStatus = "User";
            TakenBook takenBook = new TakenBook(Assistant.selectedLibraryUser, "User", instance, Assistant.today(), Assistant.nextDay(30));
            instance.update();
            takenBook.save();
        }
        return redirect(routes.UserController.workWithUserPage());
    }

    public Result takeBookFromTransfer() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        BookTransfer bookTransfer = BookTransfer.byId(data.get("TakeBook"));
        BookInstance instance = bookTransfer.bookInstance;
        instance.bookInstanceStatus = "User";
        instance.deliveryPoint = Assistant.selectedDeliveryPoint;
        instance.date = Assistant.today();
        TakenBook takenBook = new TakenBook(Assistant.selectedLibraryUser, "User", instance, Assistant.today(), Assistant.nextDay(30));
        instance.update();
        takenBook.save();
        bookTransfer.delete();
        return redirect(routes.UserController.workWithUserPage());
    }
}
