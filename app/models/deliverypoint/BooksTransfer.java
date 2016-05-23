package models.deliverypoint;


import com.avaje.ebean.Model;
import models.book.BookInstance;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class BooksTransfer extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @OneToOne
    @NotNull
    public BookInstance bookInstance;

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
}
