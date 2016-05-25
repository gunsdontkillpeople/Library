package models.book;


import akka.stream.impl.fusing.Take;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import models.deliverypoint.DeliveryPoint;
import models.user.LibraryUser;
import utils.Assistant;
import utils.Pair;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class TakenBook extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @NotNull
    public LibraryUser libraryUser;

    @NotNull
    public String takenBookStatus;

    @ManyToOne
    @NotNull
    public BookInstance bookInstance;

    @NotNull
    public Date takeDate;

    @NotNull
    public Date returnDate;

    public TakenBook(LibraryUser user, String status, BookInstance instance, Date takeDate, Date returnDate){
        this.libraryUser = user;
        this.takenBookStatus = status;
        this.bookInstance = instance;
        this.takeDate = takeDate;
        this.returnDate = returnDate;
    }

    @Override
    public String toString(){
        Book book = bookInstance.book;
        DeliveryPoint deliveryPoint = bookInstance.deliveryPoint;
        return "Title: " + book.title + ", Author: " + book.author + ", Take: " + Assistant.dateToString(takeDate) + ", Return: " +
                Assistant.dateToString(returnDate) + " (Delivery point: " +
                deliveryPoint.name + ", " + deliveryPoint.address + ": " + deliveryPoint.deliveryPointType.name + ")";
    }

    public static List<TakenBook> byUserCurrently(LibraryUser libraryUser){
        String sql = "SELECT taken_book.id AS id FROM taken_book\n" +
                "WHERE taken_book.library_user_id = :libraryUserId AND taken_book_status like 'User'";
        SqlQuery query = Ebean.createSqlQuery(sql);
        query.setParameter("libraryUserId", libraryUser.id);
        List<SqlRow> sqlRows = query.findList();
        List<TakenBook> ret = new ArrayList<>();
        for (SqlRow row : sqlRows) {
            Long id = (Long)row.get("id");
            TakenBook takenBook = (TakenBook) new Finder(String.class, TakenBook.class).byId(id);
            ret.add(takenBook);
        }
        return ret;
    }
}
