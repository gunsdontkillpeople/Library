package models.user;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class User extends Model {

    @Id
    public Long id;

    @ManyToOne
    @NotNull
    public UserCategory userCategory;

    @NotNull
    public String name;

    @NotNull
    public String middlename;

    @NotNull
    public String surname;
}
