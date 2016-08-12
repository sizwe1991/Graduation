/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import org.primefaces.model.chart.*;

import za.ac.vut.entity.*;
import za.ac.vut.facade.*;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class chart implements Serializable
{

    @EJB
    private GradStudentFacade gradStudentFacade;

    @EJB
    private CampusFacade campusFacade;

    @EJB
    private HodFacade hodFacade;

    @EJB
    private GraduatesViewFacade graduatesViewFacade;

    @EJB
    private CeremonyFacade ceremonyFacade;

    private List<Ceremony> ceremonyList;
    private List<GradStudent> gradStudentList;
    private List<Campus> campusList;
    private List<GraduatesView> graduatesViewList;

    private BarChartModel chartModel;
    private PieChartModel pieChartAbsencia, pieChartPresencia;

    private final String EXAM_RECIEVE = "EXAM:Received";

    private final String POSSIBLE = "POSSIBLE";
    private final String FRAUD_APPROVE = "Verification:Approved";

    private List<Hod> hodList;

    private String absencia = "", presencia = "";

    public chart()
    {

        gradStudentList = new ArrayList<>();
        campusList = new ArrayList<>();
        hodList = new ArrayList<>();
        ceremonyList = new ArrayList<>();
        graduatesViewList = new ArrayList<>();
    }

    @PostConstruct
    public void init()
    {

        gradStudentList = gradStudentFacade.findAll();
        campusList = campusFacade.findAll();
        hodList = hodFacade.findAll();
        ceremonyList = ceremonyFacade.findAll();
        graduatesViewList = graduatesViewFacade.findAll();

        createPieCharts();
        createAnimatedModel();
    }

    public BarChartModel getChartModel()
    {
        return chartModel;
    }

    public void setChartModel(BarChartModel chartModel)
    {
        this.chartModel = chartModel;
    }

    public PieChartModel getPieChartAbsencia()
    {
        return pieChartAbsencia;
    }

    public void setPresencia(String presencia)
    {
        this.presencia = presencia;
    }

    public void setAbsencia(String absencia)
    {
        this.absencia = absencia;
    }

    public String getPresencia()
    {
        return presencia;
    }

    public String getAbsencia()
    {
        return absencia;
    }

    public void createAnimatedModel()
    {

        chartModel = initBarModel();
        chartModel.setTitle("Vaal University of Technology: Graduations System");
        chartModel.setAnimate(true);
        chartModel.setLegendPosition("ne");
        Axis yAxis = chartModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(100);

    }

    public void createPieCharts()
    {
        createPieChartAbsencia();
        createPieChartPresencia();
    }

    public void createPieChartAbsencia()
    {
        pieChartAbsencia = new PieChartModel();

        double absenciaValue = 0.0;

        int total = 0;

        for (GraduatesView graduates : graduatesViewList)
        {

            if (graduates.getStatus().equalsIgnoreCase("no"))
            {
                total += graduates.getStudents();
            }
        }

        for (GraduatesView graduates : graduatesViewList)
        {

            if (graduates.getStatus().equalsIgnoreCase("no"))
            {

                if (graduates.getStudents() > 0)
                {
                    absenciaValue = (graduates.getStudents() * 100) / total;
                }

                String campusName = "";

                for (Campus campus : campusList)
                {
                    if (campus.getShortCode().equalsIgnoreCase(graduates.getCampus()))
                    {
                        campusName = campus.getDescription();
                    }
                }

                absencia += campusName + "=" + graduates.getStudents() + ",";

                pieChartAbsencia.set(campusName, absenciaValue);
            }

        }

        int number = absencia.length();

        if (number > 0)
        {
            number--;
        }

        absencia = absencia.substring(0, number);

        pieChartAbsencia.setFill(true);
        pieChartAbsencia.setShowDataLabels(true);
        pieChartAbsencia.setTitle("Graduating In Absencia");
        pieChartAbsencia.setLegendPosition("w");
    }

    public void createPieChartPresencia()
    {
        pieChartPresencia = new PieChartModel();

        double PresenciaValue = 0.0;

        int total = 0;

        if (!graduatesViewList.isEmpty())
        {
            for (GraduatesView graduates : graduatesViewList)
            {

                if (graduates.getStatus().equalsIgnoreCase("yes"))
                {
                    total += graduates.getStudents();
                }
            }

            for (GraduatesView graduates : graduatesViewList)
            {

                if (graduates.getStatus().equalsIgnoreCase("yes"))
                {

                    if (graduates.getStudents() > 0)
                    {
                        PresenciaValue = (graduates.getStudents() * 100) / total;
                    }

                    String campusName = "";

                    for (Campus campus : campusList)
                    {
                        if (campus.getShortCode().equalsIgnoreCase(graduates.getCampus()))
                        {
                            campusName = campus.getDescription();
                        }
                    }

                    pieChartPresencia.set(campusName, PresenciaValue);

                    presencia += campusName + "=" + graduates.getStudents() + ",";
                }

            }
        }

        int number = presencia.length();

        if (number > 0)
        {
            number--;
        }

        presencia = presencia.substring(0, number);
        pieChartPresencia.setFill(true);
        pieChartPresencia.setShowDataLabels(true);
        pieChartPresencia.setTitle("Graduating In Presencia");
        pieChartPresencia.setLegendPosition("w");
    }

    public PieChartModel getPieChartPresencia()
    {
        return pieChartPresencia;
    }

    private BarChartModel initBarModel()
    {

        BarChartModel model = new BarChartModel();

        ChartSeries possible = new ChartSeries();
        ChartSeries examRecieved = new ChartSeries();
        ChartSeries fraudVerified = new ChartSeries();
        ChartSeries confimedGraduates = new ChartSeries();

        possible.setLabel("Outstanding Documents");
        examRecieved.setLabel("Awaiting Verification");
        fraudVerified.setLabel("Awaiting Dept Head Approval");
        confimedGraduates.setLabel("Confirmed Graduates");

        for (Campus campus : campusList)
        {
            double possibleValue = 0.0;
            double examValue = 0.0;
            double fraudValue = 0.0;
            double comfirmedValue = 0.0;

            int possibleNumber = getPossible(campus).size();

            int examNumber = getExamReceived(campus).size();

            int fraudNumber = getFraudApproved(campus).size();

            int confirmedNumber = getConfirmedGraduates(campus).size();

            int total = getTotalStudents(campus).size();

            System.out.println("passed");

            if (possibleNumber > 0)
            {
                possibleValue = (possibleNumber * 100) / total;
            }

            if (examNumber > 0)
            {
                examValue = (examNumber * 100) / total;
            }

            if (fraudNumber > 0)
            {
                fraudValue = (fraudNumber * 100) / total;
            }

            if (confirmedNumber > 0)
            {
                comfirmedValue = (confirmedNumber * 100) / total;
            }

            possible.set(campus.getShortCode(), possibleValue);
            examRecieved.set(campus.getShortCode(), examValue);
            fraudVerified.set(campus.getShortCode(), fraudValue);
            confimedGraduates.set(campus.getShortCode(), comfirmedValue);

        }

        model.addSeries(possible);
        model.addSeries(examRecieved);
        model.addSeries(fraudVerified);
        model.addSeries(confimedGraduates);

        return model;
    }

    public List<GradStudent> getPossible(Campus campus)
    {
        List<GradStudent> gradStudents = new ArrayList<>();

        for (GradStudent student : gradStudentList)
        {
            if (student.getOfferingType() != null && student.getAdmitStatus() != null)
            {
                if (student.getOfferingType().charAt(0) == campus.getShortCode().charAt(0)
                        && student.getAdmitStatus().equalsIgnoreCase(POSSIBLE))
                {
                    gradStudents.add(student);
                }
            }
        }

        return gradStudents;
    }

    public List<GradStudent> getExamReceived(Campus campus)
    {
        List<GradStudent> gradStudents = new ArrayList<>();
        for (GradStudent student : gradStudentList)
        {
            if (student.getOfferingType() != null && student.getAdmitStatus() != null)
            {
                if (student.getOfferingType().charAt(0) == campus.getShortCode().charAt(0)
                        && student.getAdmitStatus().equalsIgnoreCase(EXAM_RECIEVE))
                {
                    gradStudents.add(student);
                }
            }
        }
        return gradStudents;
    }

    public List<GradStudent> getFraudApproved(Campus campus)
    {
        List<GradStudent> gradStudents = new ArrayList<>();
        for (GradStudent student : gradStudentList)
        {
            if (student.getOfferingType() != null && student.getAdmitStatus() != null)
            {
                if (student.getOfferingType().charAt(0) == campus.getShortCode().charAt(0)
                        && student.getAdmitStatus().equalsIgnoreCase(FRAUD_APPROVE))
                {
                    gradStudents.add(student);
                }
            }
        }
        return gradStudents;
    }

    public List<GradStudent> getConfirmedGraduates(Campus campus)
    {
        List<GradStudent> gradStudents = new ArrayList<>();

        if (!hodList.isEmpty())
        {
            for (Hod hod : hodList)
            {
                if (hod.getStatus().equalsIgnoreCase("Yes"))
                {
                    if (getGraduate(hod.getAdmitStno()).getOfferingType() != null)
                    {
                        if (getGraduate(hod.getAdmitStno()).getOfferingType().
                                charAt(0) == campus.getShortCode().charAt(0))
                        {
                            gradStudents.add(getGraduate(hod.getAdmitStno()));
                        }
                    }

                }
            }
        }

        return gradStudents;
    }

    public List<GradStudent> getAbsencia(Campus campus)
    {
        List<GradStudent> gradStudents = new ArrayList<>();

        if (!ceremonyList.isEmpty())
        {
            for (Ceremony ceremony : ceremonyList)
            {
                if (ceremony.getStatus().equalsIgnoreCase("No"))
                {
                    if (getGraduate(ceremony.getAdmitStno()).getOfferingType() != null)
                    {
                        if (getGraduate(ceremony.getAdmitStno()).getOfferingType().
                                charAt(0) == campus.getShortCode().charAt(0))
                        {
                            gradStudents.add(getGraduate(ceremony.getAdmitStno()));
                        }
                    }

                }
            }
        }

        return gradStudents;
    }

    public List<GradStudent> getPresencia(Campus campus)
    {
        List<GradStudent> gradStudents = new ArrayList<>();

        if (!ceremonyList.isEmpty())
        {
            for (Ceremony ceremony : ceremonyList)
            {
                if (ceremony.getStatus().equalsIgnoreCase("Yes"))
                {
                    if (getGraduate(ceremony.getAdmitStno()).getOfferingType() != null)
                    {
                        if (getGraduate(ceremony.getAdmitStno()).getOfferingType()
                                .charAt(0) == campus.getShortCode().charAt(0))
                        {
                            gradStudents.add(getGraduate(ceremony.getAdmitStno()));
                        }
                    }

                }
            }
        }
        return gradStudents;
    }

    public List<GradStudent> getTotalStudents(Campus campus)
    {
        List<GradStudent> gradStudents = new ArrayList<>();
        for (GradStudent gradStudent : gradStudentList)
        {
            if (gradStudent.getOfferingType() != null)
            {
                if (gradStudent.getOfferingType().charAt(0)
                        == campus.getShortCode().charAt(0))
                {
                    gradStudents.add(gradStudent);
                }
            }
        }
        return gradStudents;
    }

    public int getTotalAbsencia()
    {
        int number = 0;
        if (!ceremonyList.isEmpty())
        {
            for (Ceremony ceremony : ceremonyList)
            {
                if (ceremony.getStatus().equalsIgnoreCase("No"))
                {
                    number++;
                }
            }
        }
        return number;
    }

    public int getTotalPresencia()
    {
        int number = 0;

        if (!ceremonyList.isEmpty())
        {
            for (Ceremony ceremony : ceremonyList)
            {
                if (ceremony.getStatus().equalsIgnoreCase("Yes"))
                {
                    number++;
                }
            }
        }
        return number;
    }

    public GradStudent getGraduate(String stNo)
    {
        GradStudent grad = new GradStudent();

        for (GradStudent g : gradStudentList)
        {
            if (g.getAdmitStno() != null)
            {
                if (g.getAdmitStno().equalsIgnoreCase(stNo))
                {
                    grad = g;
                }
            }
        }

        return grad;
    }

}
