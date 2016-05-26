package models.deliverypoint;


import com.avaje.ebean.Model;
import models.book.Book;
import models.user.StudentUserCategoryCharacteristic;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    public static List<DeliveryPoint> all(){
        return new Model.Finder(String.class, DeliveryPoint.class).all();
    }

    public static DeliveryPoint byId(Object id){
        return (DeliveryPoint) new Finder(String.class, DeliveryPoint.class).byId(id);
    }
}
