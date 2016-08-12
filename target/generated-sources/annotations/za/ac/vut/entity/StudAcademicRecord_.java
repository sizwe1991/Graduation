package za.ac.vut.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.entity.GradStudent;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(StudAcademicRecord.class)
public class StudAcademicRecord_ { 

    public static volatile SingularAttribute<StudAcademicRecord, String> admitLevel;
    public static volatile SingularAttribute<StudAcademicRecord, String> admitOutcome;
    public static volatile SingularAttribute<StudAcademicRecord, GradStudent> gradstudId;
    public static volatile SingularAttribute<StudAcademicRecord, String> admitStno;
    public static volatile SingularAttribute<StudAcademicRecord, String> admitSubjectcode;
    public static volatile SingularAttribute<StudAcademicRecord, String> admitYear;
    public static volatile SingularAttribute<StudAcademicRecord, Integer> gradStudAcademicId;
    public static volatile SingularAttribute<StudAcademicRecord, String> admitSubjectdesc;
    public static volatile SingularAttribute<StudAcademicRecord, String> admitCreditscore;
    public static volatile SingularAttribute<StudAcademicRecord, String> admitMark;

}