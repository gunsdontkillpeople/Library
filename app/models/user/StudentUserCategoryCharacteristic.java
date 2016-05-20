package models.user;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class StudentUserCategoryCharacteristic extends Model {

    @Id
    public Long id;

    @NotNull
    public String studentFaculty;

    @NotNull
    public String studentGroup;
}
