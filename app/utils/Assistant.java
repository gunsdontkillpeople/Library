package utils;

import models.deliverypoint.DeliveryPointType;
import models.user.UserCategory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mistler on 25.05.16.
 */
public class Assistant {

    public static Date parseDateFromString(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {

            date = formatter.parse(str);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void initDB(){
        initDeliveryPointTypes();
        initLibraryUserCategories();
    }

    private static void initDeliveryPointTypes() {
        new DeliveryPointType("Delivery desk").save();
        new DeliveryPointType("Reading room").save();
    }

    private static void initLibraryUserCategories() {
        new UserCategory("Student").save();
        new UserCategory("Professor").save();
        new UserCategory("Enrollee").save();
        new UserCategory("Trainee").save();
        new UserCategory("TF listener").save();
    }

    public static Date today(){
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date nextDay(int days){
        Calendar c = Calendar.getInstance();
        c.setTime(today());
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static String dateToString(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }
}
