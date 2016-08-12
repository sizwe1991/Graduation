package za.ac.vut.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(EmailConfiguration.class)
public class EmailConfiguration_ { 

    public static volatile SingularAttribute<EmailConfiguration, String> hodSubjectReject;
    public static volatile SingularAttribute<EmailConfiguration, String> examSubject;
    public static volatile SingularAttribute<EmailConfiguration, String> fraudSubjectApprove;
    public static volatile SingularAttribute<EmailConfiguration, String> hodBodyReject;
    public static volatile SingularAttribute<EmailConfiguration, String> hodBodyApprove;
    public static volatile SingularAttribute<EmailConfiguration, String> fraudBodyReject;
    public static volatile SingularAttribute<EmailConfiguration, String> examBody;
    public static volatile SingularAttribute<EmailConfiguration, String> fraudBodyApprove;
    public static volatile SingularAttribute<EmailConfiguration, Integer> configurationNo;
    public static volatile SingularAttribute<EmailConfiguration, String> fraudSubjectReject;
    public static volatile SingularAttribute<EmailConfiguration, String> hodSubjectApprove;

}