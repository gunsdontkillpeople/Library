package models.book;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class Book extends Model {

    @Id
    public Long id;

    public String author;

    public String title;

    public Date releaseDate;

    public Double price;
}
