package models.user;


import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class LibraryUser extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    public LibraryUser(UserCategory userCategory, String name, String middlename, String surname){
        this.userCategory = userCategory;
        this.name = name;
        this.middlename = middlename;
        this.surname = surname;
    }
}
