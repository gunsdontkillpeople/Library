package models.user;


import com.avaje.ebean.Model;

import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
public class DeliveryPointUser extends Model {

    @Id
    public Long id;

    public Long userId;

    public Long takenBookId;

    public Date registerDate;

    public Date leaveDate;
}
