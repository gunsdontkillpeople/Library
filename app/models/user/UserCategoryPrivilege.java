package models.user;


import com.avaje.ebean.Model;

import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
public class UserCategoryPrivilege extends Model {

    @Id
    public Long id;

    public String privilege;
}
