package models.deliverypoint;


import com.avaje.ebean.Model;

import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
public class BooksTransfer extends Model {

    @Id
    public Long id;

    public Long bookInstanceId;

    public Long deliveryPointSrcId;

    public Long deliveryPointDstId;

    public Date sendDate;

    public Date receiveDate;
}
