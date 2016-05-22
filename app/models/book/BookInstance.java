package models.book;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import models.deliverypoint.DeliveryPoint;
import utils.Pair;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    public Long id;

    @ManyToOne
    @NotNull
    public Book book;

    @ManyToOne
    @NotNull
    public DeliveryPoint deliveryPoint;

    @NotNull
    public Date date;

    public static List<Pair<Book, Long>> instancesByDeliveryPointId(Long deliveryPointId){
        String sql = "SELECT book.id AS id, count(*) as ct FROM book_instance\n" +
                "JOIN book ON book.id = book_instance.book_id\n" +
                "WHERE book_instance.delivery_point_id = :deliveryPointId\n" +
                "GROUP BY book.id";
        SqlQuery query = Ebean.createSqlQuery(sql);
        query.setParameter("deliveryPointId", deliveryPointId);
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
}
