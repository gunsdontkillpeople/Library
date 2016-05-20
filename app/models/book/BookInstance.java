package models.book;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    @NotNull
    public Date date;
}
