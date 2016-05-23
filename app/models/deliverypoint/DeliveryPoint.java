package models.deliverypoint;


import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class DeliveryPoint extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public String name;

    @NotNull
    public String address;

    @ManyToOne
    @NotNull
    public DeliveryPointType deliveryPointType;

    @Override
    public String toString(){
        return "Name: " + name + "\t\tType: " + deliveryPointType.name;
    }
}
