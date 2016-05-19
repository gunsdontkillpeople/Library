package models.user;


import com.avaje.ebean.Model;

import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
public class UserCategory extends Model {

    @Id
    public Long id;

    public Long userCategoryPrivilegeId;

    public Long userCategoryCharacteristicId;

    public String category;
}
