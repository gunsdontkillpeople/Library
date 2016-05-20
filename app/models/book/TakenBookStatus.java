package models.book;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class TakenBookStatus extends Model {

    @Id
    public Long id;

    @NotNull
    public String name;
}
