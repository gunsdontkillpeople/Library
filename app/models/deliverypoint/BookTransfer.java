package models.deliverypoint;


import com.avaje.ebean.Model;
import models.book.BookInstance;
import models.user.LibraryUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
}
