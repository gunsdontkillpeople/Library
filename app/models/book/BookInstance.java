package models.book;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import models.deliverypoint.DeliveryPoint;
import utils.Pair;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class BookInstance extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @NotNull
    public Book book;

    @ManyToOne
    public DeliveryPoint deliveryPoint;

    public Date date;

    public static List<Pair<Book, Long>> instancesByDeliveryPoint(DeliveryPoint deliveryPoint){
        String sql = "SELECT book.id AS id, count(*) as ct FROM book_instance\n" +
                "JOIN book ON book.id = book_instance.book_id\n" +
                "WHERE book_instance.delivery_point_id = :deliveryPointId\n" +
                "GROUP BY book.id";
        SqlQuery query = Ebean.createSqlQuery(sql);
        query.setParameter("deliveryPointId", deliveryPoint.id);
        List<SqlRow> sqlRows = query.findList();
        List<Pair<Book, Long>> ret = new ArrayList<>();
        for (SqlRow row : sqlRows) {
            Long id = (Long)row.get("id");
            Book book = (Book) new Finder(String.class, Book.class).byId(id);
            Long count = (Long) row.get("ct");
            ret.add(new Pair<>(book, count));
        }
        return ret;
    }

    public static List<Pair<DeliveryPoint, Long>> instancesByBook(Book book){
        String sql = "SELECT delivery_point.id AS id, count(*) as ct FROM book_instance\n" +
                "JOIN delivery_point ON delivery_point.id = book_instance.delivery_point_id\n" +
                "WHERE book_instance.book_id = :bookId\n" +
                "GROUP BY delivery_point.id";
        SqlQuery query = Ebean.createSqlQuery(sql);
        query.setParameter("bookId", book.id);
        List<SqlRow> sqlRows = query.findList();
        List<Pair<DeliveryPoint, Long>> ret = new ArrayList<>();
        for (SqlRow row : sqlRows) {
            Long id = (Long)row.get("id");
            DeliveryPoint deliveryPoint = (DeliveryPoint) new Finder(String.class, DeliveryPoint.class).byId(id);
            Long count = (Long) row.get("ct");
            ret.add(new Pair<>(deliveryPoint, count));
        }
        return ret;
    }

    public static List<BookInstance> instancesByDeliveryPointAndBook(DeliveryPoint deliveryPoint, Book book){
        String sql = "SELECT book_instance.id AS id FROM book_instance\n" +
                "JOIN delivery_point ON delivery_point.id = book_instance.delivery_point_id\n" +
                "WHERE book_instance.book_id = :bookId AND book_instance.delivery_point_id = :deliveryPointId";
        SqlQuery query = Ebean.createSqlQuery(sql);
        query.setParameter("bookId", book.id);
        query.setParameter("deliveryPointId", deliveryPoint.id);
        List<SqlRow> sqlRows = query.findList();
        List<BookInstance> ret = new ArrayList<>();
        for (SqlRow row : sqlRows) {
            Long id = (Long)row.get("id");
            ret.add((BookInstance) new Finder(String.class, BookInstance.class).byId(id));
        }
        return ret;
    }
}
