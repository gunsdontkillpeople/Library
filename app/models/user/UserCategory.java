package models.user;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class UserCategory extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public String name;

    public UserCategory(String name){
        this.name = name;
    }

    public static List<UserCategory> all(){
        return new Model.Finder(String.class, UserCategory.class).all();
    }

    public static UserCategory byId(Object id){
        return (UserCategory) new Finder(String.class, UserCategory.class).byId(id);
    }
}
