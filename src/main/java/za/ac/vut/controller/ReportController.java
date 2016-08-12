/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.*;
import za.ac.vut.entity.*;
import za.ac.vut.facade.*;
import za.ac.vut.util.Status;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class ReportController {

    @EJB
    private HodFacade hodFacade;

    @EJB
    private GradStudentFacade gradStudentFacade;

    @EJB
    private CampusFacade campusFacade;

    @EJB
    private FraudFacade fraudFacade;

    @EJB
    private CeremonyFacade ceremonyFacade;

    private List<Hod> hodList = new ArrayList<>();
    private List<GradStudent> gradStudentList = new ArrayList<>();
    private List<Campus> campList = new ArrayList<>();
    private List<Fraud> fraudLst = new ArrayList<>();
    private List<Ceremony> ceremonyList = new ArrayList<>();

    private String report, campus;

    @PostConstruct
    public void init() {
        ceremonyList = ceremonyFacade.findAll();
        fraudLst = fraudFacade.findAll();
        campList = campusFacade.findAll();
        hodList = hodFacade.findAll();
        gradStudentList = gradStudentFacade.findAll();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public List<Hod> getHodList() {
        return hodList;
    }

    public List<Hod> getApprovedStudents() {

        List<Hod> approvedStudList = new ArrayList<>();
        for (Hod h : hodList) {
            if (h.getStatus() != null && h.getAdmitStno() != null && !campus.equalsIgnoreCase("All")) {
                if (h.getStatus().equalsIgnoreCase("APPROVED")) {
                    if (getGradStudent(h.getAdmitStno()).getOfferingType().charAt(0)
                            == campus.charAt(0)) {
                        approvedStudList.add(h);
                    }
                }
            } else if (h.getStatus() != null && h.getAdmitStno() != null) {
                if (h.getStatus().equalsIgnoreCase("APPROVED")) {

                    approvedStudList.add(h);

                }
            }
        }

        Collections.sort(approvedStudList, new Comparator<Hod>() {
            @Override
            public int compare(Hod h1, Hod h2) {
                return getGradStudent(h1.getAdmitStno()).getOfferingType().
                        compareTo(getGradStudent(h2.getAdmitStno()).getOfferingType());
            }
        });

        Collections.sort(approvedStudList, new Comparator<Hod>() {
            @Override
            public int compare(Hod h1, Hod h2) {
                return getGradStudent(h1.getAdmitStno()).getAdmitQualcode().
                        compareTo(getGradStudent(h2.getAdmitStno()).getAdmitQualcode());
            }
        });

        return approvedStudList;
    }

    public List<Hod> getRejectedStudents() {
        List<Hod> approvedStudList = new ArrayList<>();

        for (Hod h : hodList) {
            if (h.getStatus() != null && h.getAdmitStno() != null && !campus.equalsIgnoreCase("All")) {
                if (h.getStatus().equalsIgnoreCase("REJECTED")) {

                    if (getGradStudent(h.getAdmitStno()).getOfferingType().charAt(0)
                            == campus.charAt(0)) {
                        approvedStudList.add(h);
                    }
                }
            } else if (h.getStatus() != null && h.getAdmitStno() != null) {
                if (h.getStatus().equalsIgnoreCase("REJECTED")) {

                    approvedStudList.add(h);

                }
            }

        }

        Collections.sort(approvedStudList, new Comparator<Hod>() {
            @Override
            public int compare(Hod h1, Hod h2) {
                return getGradStudent(h1.getAdmitStno()).getOfferingType().
                        compareTo(getGradStudent(h2.getAdmitStno()).getOfferingType());
            }
        });

        Collections.sort(approvedStudList, new Comparator<Hod>() {
            @Override
            public int compare(Hod h1, Hod h2) {
                return getGradStudent(h1.getAdmitStno()).getAdmitQualcode().
                        compareTo(getGradStudent(h2.getAdmitStno()).getAdmitQualcode());
            }
        });

        return approvedStudList;
    }

    public List<Fraud> getFraudPassed() {
        List<Fraud> fraudList = new ArrayList<>();

        for (Fraud f : fraudLst) {
            if (f.getStatus() != null && f.getAdmitStno() != null && !campus.equalsIgnoreCase("All")) {
                if (f.getStatus().equalsIgnoreCase("APPROVED")) {
                    if (getGradStudent(f.getAdmitStno()).getOfferingType().charAt(0)
                            == campus.charAt(0)) {
                        fraudList.add(f);
                    }
                }
            } else if (f.getStatus() != null && f.getAdmitStno() != null) {
                if (f.getStatus().equalsIgnoreCase("APPROVED")) {

                    fraudList.add(f);

                }
            }

        }

        Collections.sort(fraudList, new Comparator<Fraud>() {
            @Override
            public int compare(Fraud f1, Fraud f2) {
                return getGradStudent(f1.getAdmitStno()).getOfferingType().
                        compareTo(getGradStudent(f2.getAdmitStno()).getOfferingType());
            }
        });

        Collections.sort(fraudList, new Comparator<Fraud>() {
            @Override
            public int compare(Fraud f1, Fraud f2) {
                return getGradStudent(f1.getAdmitStno()).getAdmitQualcode().
                        compareTo(getGradStudent(f2.getAdmitStno()).getAdmitQualcode());
            }
        });

        return fraudList;
    }

    public List<Fraud> getFraudFailed() {

        List<Fraud> fraudList = new ArrayList<>();

        for (Fraud f : fraudLst) {
            if (f.getStatus() != null && f.getAdmitStno() != null && !campus.equalsIgnoreCase("All")) {
                if (f.getStatus() != null && f.getAdmitStno() != null && campus != null) {

                    if (f.getStatus().equalsIgnoreCase("REJECTED")) {
                        if (getGradStudent(f.getAdmitStno()).getOfferingType().charAt(0)
                                == campus.charAt(0)) {
                            fraudList.add(f);
                        }
                    }
                }
            } else if (f.getStatus() != null && f.getAdmitStno() != null) {
                if (f.getStatus().equalsIgnoreCase("REJECTED")) {

                    fraudList.add(f);

                }
            }

        }

        Collections.sort(fraudList, new Comparator<Fraud>() {
            @Override
            public int compare(Fraud f1, Fraud f2) {
                return getGradStudent(f1.getAdmitStno()).getOfferingType().
                        compareTo(getGradStudent(f2.getAdmitStno()).getOfferingType());
            }
        });

        Collections.sort(fraudList, new Comparator<Fraud>() {
            @Override
            public int compare(Fraud f1, Fraud f2) {
                return getGradStudent(f1.getAdmitStno()).getAdmitQualcode().
                        compareTo(getGradStudent(f2.getAdmitStno()).getAdmitQualcode());
            }
        });

        return fraudList;
    }

    public List<Ceremony> getCeremonyPresencia() {
        List<Ceremony> ceremonies = new ArrayList<>();

        for (Ceremony ceremony : ceremonyList) {
            if (!campus.equalsIgnoreCase("All")) {
                if (ceremony.getStatus().equalsIgnoreCase("Yes")) {
                    if (getGradStudent(ceremony.getAdmitStno()).getOfferingType().charAt(0)
                            == campus.charAt(0)) {
                        ceremonies.add(ceremony);
                    }
                }
            } else if (ceremony.getStatus().equalsIgnoreCase("Yes")) {

                ceremonies.add(ceremony);

            }

        }

        Collections.sort(ceremonies, new Comparator<Ceremony>() {
            @Override
            public int compare(Ceremony c1, Ceremony c2) {
                return getGradStudent(c1.getAdmitStno()).getOfferingType().
                        compareTo(getGradStudent(c2.getAdmitStno()).getOfferingType());
            }
        });

        Collections.sort(ceremonies, new Comparator<Ceremony>() {
            @Override
            public int compare(Ceremony c1, Ceremony c2) {
                return getGradStudent(c1.getAdmitStno()).getAdmitQualcode().
                        compareTo(getGradStudent(c2.getAdmitStno()).getAdmitQualcode());
            }
        });

        return ceremonies;
    }

    public List<GradStudent> getPossible() {
        Status status = new Status();

        List<GradStudent> students = new ArrayList<>();
        for (GradStudent objGradStudent : gradStudentList) {
            if (!campus.equalsIgnoreCase("All") && objGradStudent.getOfferingType() != null
                    && objGradStudent.getAdmitStatus() != null) {
                if (objGradStudent.getOfferingType().charAt(0) == campus.charAt(0)
                        && objGradStudent.getAdmitStatus().equalsIgnoreCase(status.getPOSSIBLE())) {
                    students.add(objGradStudent);

                }

            } else if (objGradStudent.getAdmitStatus() != null) {
                if (objGradStudent.getAdmitStatus().equalsIgnoreCase(status.getPOSSIBLE())) {
                    students.add(objGradStudent);
                }
            }
        }

        Collections.sort(students, new Comparator<GradStudent>() {
            @Override
            public int compare(GradStudent g1, GradStudent g2) {
                return g1.getOfferingType().compareTo(g2.getOfferingType());
            }
        });

        Collections.sort(students, new Comparator<GradStudent>() {
            @Override
            public int compare(GradStudent g1, GradStudent g2) {
                return g1.getAdmitQualcode().compareTo(g2.getAdmitQualcode());
            }
        });

        return students;
    }

    public String getCampus(String offeringType) {
        String value = "";
        for (Campus objCampus : campList) {
            if (offeringType.charAt(0) == objCampus.getShortCode().charAt(0)) {
                value = objCampus.getDescription();
            }
        }
        return value;
    }

    public List<Ceremony> getCeremonyAbsencia() {
        List<Ceremony> ceremonies = new ArrayList<>();

        for (Ceremony ceremony : ceremonyList) {
            if (!campus.equalsIgnoreCase("All")) {
                if (ceremony.getStatus().equalsIgnoreCase("No")) {
                    if (getGradStudent(ceremony.getAdmitStno()).getOfferingType().charAt(0)
                            == campus.charAt(0)) {
                        ceremonies.add(ceremony);
                    }
                }
            } else if (ceremony.getStatus().equalsIgnoreCase("No")) {

                ceremonies.add(ceremony);

            }
        }

        Collections.sort(ceremonies, new Comparator<Ceremony>() {
            @Override
            public int compare(Ceremony c1, Ceremony c2) {
                return getGradStudent(c1.getAdmitStno()).getOfferingType().
                        compareTo(getGradStudent(c2.getAdmitStno()).getOfferingType());
            }
        });

        Collections.sort(ceremonies, new Comparator<Ceremony>() {
            @Override
            public int compare(Ceremony c1, Ceremony c2) {
                return getGradStudent(c1.getAdmitStno()).getAdmitQualcode().
                        compareTo(getGradStudent(c2.getAdmitStno()).getAdmitQualcode());
            }
        });

        return ceremonies;
    }

    private GradStudent getGradStudent(String stno) {
        GradStudent g = new GradStudent();
        for (GradStudent myStudent : gradStudentList) {
            if (myStudent.getAdmitStno() != null) {
                if (myStudent.getAdmitStno().equalsIgnoreCase(stno)) {
                    g = myStudent;
                }
            }

        }
        return g;
    }

//    private Campus getCampusObj(String desc) {
//        Campus objCampus = new Campus();
//        System.out.println(desc);
//        for (Campus mCampus : campList) {
//            if (mCampus.getDescription().equalsIgnoreCase(desc)) {
//                objCampus = mCampus;
//            }
//        }
//        System.out.println(objCampus.getCampusCode());
//        return objCampus;
//    }
    public String formatDate(String myDate) {
        String year = myDate.substring(24);
        String month = myDate.substring(4, 8);
        String day = myDate.substring(8, 11);
        return day + "-" + month + "-" + year;
    }
}
