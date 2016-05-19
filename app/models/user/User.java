package models.user;


import com.avaje.ebean.Model;

import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
public class User extends Model {

    @Id
    public Long id;

    public Long userCategoryId;

    public String name;

    public String middlename;

    public String surname;
}
