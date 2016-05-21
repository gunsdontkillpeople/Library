package models.deliverypoint;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class DeliveryPoint extends Model {

    @Id
    public Long id;

    @NotNull
    public String name;

    @NotNull
    public String address;

    @ManyToOne
    @NotNull
    public DeliveryPointType deliveryPointType;
}
