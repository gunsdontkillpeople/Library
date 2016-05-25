package models.user;


import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
@Entity
public class ProfessorUserCategoryCharacteristic extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @OneToOne
    public LibraryUser libraryUser;

    @NotNull
    public String professorChair;

    @NotNull
    public String professorRank;

    @NotNull
    public String professorDegree;

    public ProfessorUserCategoryCharacteristic(LibraryUser user, String chair, String rank, String degree){
        libraryUser = user;
        professorChair = chair;
        professorDegree = degree;
        professorRank = rank;
    }
}
