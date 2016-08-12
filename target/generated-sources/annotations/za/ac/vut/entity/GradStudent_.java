package za.ac.vut.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.entity.Campusqualification;
import za.ac.vut.entity.StudAcademicRecord;
import za.ac.vut.entity.StudMatric;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(GradStudent.class)
public class GradStudent_ { 

    public static volatile SingularAttribute<GradStudent, String> admitIdpassport;
    public static volatile SingularAttribute<GradStudent, String> admitTitle;
    public static volatile SingularAttribute<GradStudent, Integer> gradstudId;
    public static volatile SingularAttribute<GradStudent, String> offeringType;
    public static volatile CollectionAttribute<GradStudent, StudMatric> studMatricCollection;
    public static volatile SingularAttribute<GradStudent, String> admitSurname;
    public static volatile CollectionAttribute<GradStudent, StudAcademicRecord> studAcademicRecordCollection;
    public static volatile SingularAttribute<GradStudent, Campusqualification> campusQualId;
    public static volatile SingularAttribute<GradStudent, String> admitStno;
    public static volatile SingularAttribute<GradStudent, String> admitInit;
    public static volatile SingularAttribute<GradStudent, String> comment;
    public static volatile SingularAttribute<GradStudent, String> admitQualcode;
    public static volatile SingularAttribute<GradStudent, String> admitStatus;
    public static volatile SingularAttribute<GradStudent, String> admitFullnames;
    public static volatile SingularAttribute<GradStudent, String> admitQualdesc;

}