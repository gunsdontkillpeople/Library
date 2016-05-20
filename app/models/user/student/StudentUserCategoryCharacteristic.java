package models.user.student;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class StudentUserCategoryCharacteristic extends Model {

    @Id
    public Long id;

    public Long studentFacultyId;

    public Long studentGroupId;
}
