package models.user.professor;


import com.avaje.ebean.Model;

import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by mistler on 19.05.16.
 */
public class ProfessorUserCategoryCharacteristic extends Model {

    @Id
    public Long id;

    public Long professorChairId;

    public Long professorRankId;

    public Long professorDegreeId;
}
