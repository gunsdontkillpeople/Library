package controllers;

import models.deliverypoint.DeliveryPointType;
import models.user.UserCategory;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Assistant;
import views.html.index;

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
