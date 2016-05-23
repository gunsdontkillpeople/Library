package models.user;


import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
}
