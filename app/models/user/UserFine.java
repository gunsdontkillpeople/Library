package models.user;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import utils.Assistant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class UserFine extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @NotNull
    public LibraryUser libraryUser;

    public Date fineStart;

    public Date fineEnd;

    public Double price;

    public UserFine(LibraryUser libraryUser, Date fineStart, Date fineEnd, Double price){
        this.libraryUser = libraryUser;
        this.fineStart = fineStart;
        this.fineEnd = fineEnd;
        this.price = price;
    }

    @Override
    public String toString(){
        if(price != null){
            return "Fine: Pirce: " + price;
        }else{
            return "Fine: Start: " + Assistant.dateToString(fineStart) + ", End: " + Assistant.dateToString(fineEnd);
        }
    }

    public static List<UserFine> all(){
        return new Model.Finder(String.class, UserFine.class).all();
    }

    public static UserFine byId(Object id){
        return (UserFine) new Finder(String.class, UserFine.class).byId(id);
    }

    public static List<UserFine> byUser(LibraryUser selectedLibraryUser) {
        String sql = "SELECT user_fine.id AS id FROM user_fine\n" +
                "JOIN library_user ON library_user.id = library_user_id\n" +
                "WHERE library_user_id = :selectedLibraryUserId";
        SqlQuery query = Ebean.createSqlQuery(sql);
        query.setParameter("selectedLibraryUserId", selectedLibraryUser.id);
        List<SqlRow> sqlRows = query.findList();
        List<UserFine> ret = new ArrayList<>();
        for (SqlRow row : sqlRows) {
            Long id = (Long)row.get("id");
            ret.add((UserFine) new Finder(String.class, UserFine.class).byId(id));
        }
        return ret;
    }

    public static String endDateByUser(LibraryUser user){
        if(user == null){
            return null;
        }
        List<UserFine> fines = byUser(user);
        Date today = Assistant.today();
        for(UserFine fine : fines){
            if(fine.price == null)
                if(fine.fineEnd.after(today))
                    return Assistant.dateToString(fine.fineEnd);
        }
        return null;
    }
}
