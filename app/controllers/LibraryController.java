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
public class LibraryController extends Controller {

    public LibraryController() {
        if (DeliveryPointType.all().isEmpty()) {
            Assistant.initDB();
        }
        Assistant.selectedDeliveryPointType = DeliveryPointType.all().get(0);
        Assistant.selectedUserCategory = UserCategory.all().get(0);
    }

    public Result index() {
        String bookString = (Assistant.selectedBook == null) ? "Not selected" : Assistant.selectedBook.toString();
        String deliveryPointString = (Assistant.selectedDeliveryPoint == null) ? "Not selected" : Assistant.selectedDeliveryPoint.toString();
        return ok(index.render(bookString, deliveryPointString));
    }
}
