package models.book;


import com.avaje.ebean.Model;
import models.user.LibraryUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class TakenBook extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @OneToOne
    @NotNull
    public LibraryUser libraryUser;

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
