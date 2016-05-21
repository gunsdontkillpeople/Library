package models.deliverypoint;


import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.EnumValue;

import javax.persistence.Entity;

/**
 * Created by mistler on 19.05.16.
 */

public enum DeliveryPointType{
    @EnumValue("DESK")
    DELIVERY_DESK("DESK"),
    @EnumValue("ROOM")
    READING_ROOM("ROOM");

    String dbValue;

    DeliveryPointType(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getValue() {
        return dbValue;
    }
}
