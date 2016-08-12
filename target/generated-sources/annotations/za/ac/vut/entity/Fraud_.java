package za.ac.vut.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(Fraud.class)
public class Fraud_ { 

    public static volatile SingularAttribute<Fraud, Date> date;
    public static volatile SingularAttribute<Fraud, String> admitStno;
    public static volatile SingularAttribute<Fraud, String> time;
    public static volatile SingularAttribute<Fraud, Integer> fraudId;
    public static volatile SingularAttribute<Fraud, String> username;
    public static volatile SingularAttribute<Fraud, String> status;

}