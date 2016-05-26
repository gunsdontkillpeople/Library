package models.book;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class Book extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @NotNull
    public String author;

    @NotNull
    public String title;

    public Date releaseDate;

    public Double price;

    @Override
    public String toString(){
        return "Author: " + author + ", Title: " + title;
    }

    public static List<Book> all(){
        return new Model.Finder(String.class, Book.class).all();
    }

    public static Book byId(Object id){
        return (Book) new Finder(String.class, Book.class).byId(id);
    }
}
