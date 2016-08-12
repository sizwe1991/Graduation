package za.ac.vut.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(GradLog.class)
public class GradLog_ { 

    public static volatile SingularAttribute<GradLog, String> logType;
    public static volatile SingularAttribute<GradLog, Date> logDate;
    public static volatile SingularAttribute<GradLog, Integer> logId;
    public static volatile SingularAttribute<GradLog, String> comment;
    public static volatile SingularAttribute<GradLog, String> logTime;

}