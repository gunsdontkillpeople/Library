package models.book;


import com.avaje.ebean.Model;

import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
public class TakenBook extends Model {

    @Id
    public Long id;

    public Long userId;

    public Long takeBookStatus;

    public Long bookInstanceId;

    public Date takeDate;

    public Date returnDate;
}
