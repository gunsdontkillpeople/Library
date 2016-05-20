package models.book;


import com.avaje.ebean.Model;
import models.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class TakenBook extends Model {

    @Id
    public Long id;

    @OneToOne
    @NotNull
    public User user;

    @ManyToOne
    @NotNull
    public TakenBookStatus takenBookStatus;

    @OneToOne
    @NotNull
    public BookInstance bookInstance;

    @NotNull
    public Date takeDate;

    @NotNull
    public Date returnDate;
}
