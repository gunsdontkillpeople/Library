package models.book;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
public class Book extends Model {

    @Id
    public Long id;

    public String author;

    public String title;

    public Date release_date;

    public Double price;
}
