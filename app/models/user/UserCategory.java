package models.user;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class UserCategory extends Model {

    @Id
    public Long id;

    public Long userCategoryPrivilegeId;

    public Long userCategoryCharacteristicId;

    public String name;
}
