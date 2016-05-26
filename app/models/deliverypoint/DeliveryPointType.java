package models.deliverypoint;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class DeliveryPointType extends Model{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public String name;

    public DeliveryPointType(String name){
        this.name = name;
    }

    public static List<DeliveryPointType> all(){
        return new Model.Finder(String.class, DeliveryPointType.class).all();
    }

    public static DeliveryPointType byId(Object id){
        return (DeliveryPointType) new Finder(String.class, DeliveryPointType.class).byId(id);
    }
}
