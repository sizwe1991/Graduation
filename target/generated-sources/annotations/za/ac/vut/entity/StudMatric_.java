package za.ac.vut.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.entity.GradStudent;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(StudMatric.class)
public class StudMatric_ { 

    public static volatile SingularAttribute<StudMatric, String> admitSubjectlevel;
    public static volatile SingularAttribute<StudMatric, GradStudent> gradstudId;
    public static volatile SingularAttribute<StudMatric, Integer> gradStudMatricId;
    public static volatile SingularAttribute<StudMatric, String> admitStno;
    public static volatile SingularAttribute<StudMatric, String> admitSubjectdesc;

}