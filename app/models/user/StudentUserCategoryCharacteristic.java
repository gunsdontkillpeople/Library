package models.user;


import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class StudentUserCategoryCharacteristic extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @OneToOne
    public LibraryUser libraryUser;

    @NotNull
    public String studentFaculty;

    @NotNull
    public String studentGroup;

    public StudentUserCategoryCharacteristic(LibraryUser user, String faculty, String group){
        libraryUser = user;
        studentFaculty = faculty;
        studentGroup = group;
    }

    public static List<StudentUserCategoryCharacteristic> all(){
        return new Model.Finder(String.class, StudentUserCategoryCharacteristic.class).all();
    }

    public static StudentUserCategoryCharacteristic byId(Object id){
        return (StudentUserCategoryCharacteristic) new Finder(String.class, StudentUserCategoryCharacteristic.class).byId(id);
    }
}
