package controllers;

import models.book.Book;
import models.book.BookInstance;
import models.deliverypoint.DeliveryPoint;
import models.deliverypoint.DeliveryPointType;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Assistant;
import utils.Pair;
import views.html.addDeliveryPoint;
import views.html.workWithDeliveryPoint;

import java.util.List;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class DeliveryPointController extends Controller {

    public Result addDeliveryPointPage() {
        List<DeliveryPoint> points = DeliveryPoint.all();
        List<DeliveryPointType> pointTypes = DeliveryPointType.all();
        return ok(addDeliveryPoint.render(pointTypes, points, Assistant.selectedDeliveryPoint));
    }

    public Result workWithDeliveryPointPage() {
        List<Pair<Book, Long>> booksByDeliveryPoint = null;
        if(Assistant.selectedDeliveryPoint != null){
            booksByDeliveryPoint = BookInstance.byDeliveryPoint(Assistant.selectedDeliveryPoint);
        }
        List<DeliveryPoint> points = DeliveryPoint.all();
        return ok(workWithDeliveryPoint.render(Assistant.selectedDeliveryPoint, points, booksByDeliveryPoint));
    }
    public Result addDeliveryPoint() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.name = data.get("name");
        deliveryPoint.address = data.get("address");
        deliveryPoint.deliveryPointType = DeliveryPointType.byId(data.get("type"));
        deliveryPoint.save();
        return redirect(routes.DeliveryPointController.addDeliveryPointPage());
    }

    public Result selectDeliveryPoint() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Assistant.selectedDeliveryPoint = DeliveryPoint.byId(data.get("Delivery Points"));
        return redirect(routes.DeliveryPointController.workWithDeliveryPointPage());
    }

    public Result selectDeliveryPointSrc() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Assistant.selectedDeliveryPointSrc = DeliveryPoint.byId(data.get("Delivery Points"));
        return redirect(routes.BookController.transfersPage());
    }

    public Result selectDeliveryPointDst() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Assistant.selectedDeliveryPoint = DeliveryPoint.byId(data.get("Delivery Points"));
        return redirect(routes.BookController.transfersPage());
    }

    public Result selectDeliveryPointType() {
        DynamicForm form = Form.form().bindFromRequest();
        Map<String, String> data = form.data();
        Assistant.selectedDeliveryPointType = DeliveryPointType.byId(data.get("Delivery Points"));
        return redirect(routes.DeliveryPointController.addDeliveryPointPage());
    }
}
