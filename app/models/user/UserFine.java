package models.user;


import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class UserFine extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @NotNull
    public LibraryUser libraryUser;

    @NotNull
    public Date start;

    @NotNull
    public Date end;

    @NotNull
    public Double price;
}
