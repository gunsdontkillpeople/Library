package models.user;


import com.avaje.ebean.Model;
import models.book.Book;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

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

    public static List<LibraryUser> all(){
        return new Model.Finder(String.class, LibraryUser.class).all();
    }

    public static LibraryUser byId(Object id){
        return (LibraryUser) new Finder(String.class, LibraryUser.class).byId(id);
    }
}
