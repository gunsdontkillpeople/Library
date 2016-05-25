package models.deliverypoint;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import models.book.Book;
import models.book.BookInstance;
import models.user.LibraryUser;
import utils.Assistant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class BookTransfer extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @OneToOne
    @NotNull
    public BookInstance bookInstance;

    @ManyToOne
    @NotNull
    public LibraryUser libraryUser;

    @ManyToOne
    @NotNull
    public DeliveryPoint srcDeliveryPoint;

    @ManyToOne
    @NotNull
    public DeliveryPoint dstDeliveryPoint;

    @NotNull
    public Date sendDate;

    @NotNull
    public Date receiveDate;

    public BookTransfer(BookInstance bookInstance, LibraryUser libraryUser, DeliveryPoint src, DeliveryPoint dst, Date sendDate, Date receiveDate){
        this.bookInstance = bookInstance;
        this.libraryUser = libraryUser;
        this.srcDeliveryPoint = src;
        this.dstDeliveryPoint = dst;
        this.sendDate = sendDate;
        this.receiveDate = receiveDate;
    }

    @Override
    public String toString(){
        Book book = bookInstance.book;
        return "Author: " + book.author + ", Title: " + book.title +
                ", From: " + srcDeliveryPoint.name + ", To: " + dstDeliveryPoint.name +
                " (" + Assistant.dateToString(sendDate) + " up to " + Assistant.dateToString(receiveDate) + ")";
    }

    public static List<BookTransfer> byUser(LibraryUser selectedLibraryUser) {
        String sql = "SELECT book_transfer.id AS id FROM book_transfer\n" +
                "WHERE book_transfer.library_user_id = :libraryUserId";
        SqlQuery query = Ebean.createSqlQuery(sql);
        query.setParameter("libraryUserId", selectedLibraryUser.id);
        List<SqlRow> sqlRows = query.findList();
        List<BookTransfer> ret = new ArrayList<>();
        for (SqlRow row : sqlRows) {
            Long id = (Long)row.get("id");
            ret.add((BookTransfer) new Finder(String.class, BookTransfer.class).byId(id));
        }
        return ret;
    }
}
