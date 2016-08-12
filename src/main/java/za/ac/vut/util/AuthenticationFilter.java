/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.util;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import za.ac.vut.controller.*;

/**
 *
 * @author 2015127
 */
@WebFilter(urlPatterns
        = {
            "/fraud/*", "/admin/*", "/hod/*", "/exam/*", "/dashboard/*", "/changePassword/*"
        })
public class AuthenticationFilter implements Filter {

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    private final String ADMIN = "ADMIN";
    private final String HOD = "HOD";
    private final String EXAM = "EXAM";
    private final String FRAUD = "FRAUD";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestPath = req.getRequestURI();
        //Checks if the requested url needs authentication.
        //  boolean requiresAuthentication = isDefaultPages(requestPath);

        //Returns the loggedin Session
        Login loginController = getLoggedSession(req);

        try {

            if (loginController.getObjUser() != null) {

                if (loginController.getObjUser().getRole().equalsIgnoreCase(ADMIN)) {
                    if (isAdminPageRequest(requestPath)) {
                        chain.doFilter(request, response);
                    } else if (isDefault(requestPath)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect(req.getContextPath() + "/exception/authentication.xhtml");
                    }

                } else if (loginController.getObjUser().getRole().equalsIgnoreCase(FRAUD)) {

                    if (isFraudPageRequest(requestPath)) {
                        chain.doFilter(request, response);
                    } else if (isDefault(requestPath)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect(req.getContextPath() + "/exception/authentication.xhtml");
                    }
                } else if (loginController.getObjUser().getRole().equalsIgnoreCase(HOD)) {

                    if (isHODPageRequest(requestPath)) {
                        chain.doFilter(request, response);
                    } else if (isDefault(requestPath)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect(req.getContextPath() + "/exception/authentication.xhtml");
                    }
                } else if (loginController.getObjUser().getRole().equalsIgnoreCase(EXAM)) {
                    if (isExamPageRequest(requestPath)) {
                        chain.doFilter(request, response);
                    } else if (isDefault(requestPath)) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect(req.getContextPath() + "/exception/authentication.xhtml");
                    }
                } else {

                    res.sendRedirect(req.getContextPath() + "/exception/authentication.xhtml");

                }

            } else {
  
                res.sendRedirect(req.getContextPath() + "/index.xhtml");

            }

        } catch (NullPointerException s) {
//            if (loginController.getGradStudent() != null) {
//
//                if (isStudPageRequest(requestPath)) {
//                    chain.doFilter(request, response);
//                } else {
//                    res.sendRedirect(req.getContextPath() + "/exception/authentication.xhtml");
//                }
//            } else {
//                System.out.println("all");
            res.sendRedirect(req.getContextPath() + "/index.xhtml");

//            }
        }

    }

    @Override
    public void destroy() {
        this.config = null;
    }

    private Login getLoggedSession(HttpServletRequest req) {
        Login loginController = (Login) req.getSession().getAttribute("login");

        return loginController;
    }

//    private boolean isDefaultPages(String url) {
//        String[] validNonAuthenticationUrls
//                = {
//                    "index.xhtml"
//                };
//        for (String validUrl : validNonAuthenticationUrls) {
//            if (url.endsWith(validUrl)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
    private boolean isAdminPageRequest(String url) {
        String[] authenticationUrls
                = {
                    "addUser.xhtml",
                    "linkHOD.xhtml",
                    "manageQual.xhtml",
                    "hodLink.xhtml",
                    "emailconfiguration.xhtml",
                    "manageUser.xhtml",
                    "updateAdmin.xhtml",};
        for (String validUrl : authenticationUrls) {
            if (url.endsWith(validUrl)) {

                return true;

            }
        }
        return false;
    }

    private boolean isDefault(String url) {
        String[] authenticationUrls
                = {
                    "dashboard.xhtml",
                    "reports.xhtml",
                    "changePwd.xhtml",};
        for (String validUrl : authenticationUrls) {
            if (url.endsWith(validUrl)) {

                return true;

            }
        }
        return false;
    }

    private boolean isHODPageRequest(String url) {
        String[] authenticationUrls
                = {
                    "changeStatus.xhtml",
                    "students.xhtml", 
                    "viewStudents.xhtml",};
        for (String validUrl : authenticationUrls) {
            if (url.endsWith(validUrl)) {

                return true;

            }
        }
        return false;
    }

    private boolean isFraudPageRequest(String url) {
        boolean value = false;
        String[] authenticationUrls
                = {
                    "changeStatus.xhtml",
                    "fraud.xhtml",};

        for (String validUrl : authenticationUrls) {
            if (url.endsWith(validUrl)) {

                value = true;

            }
        }
        return value;
    }

    private boolean isExamPageRequest(String url) {
        boolean value = false;

        String[] authenticationUrls
                = {
                    "exams.xhtml",
                    "changeStatus.xhtml",};
        for (String validUrl : authenticationUrls) {
            if (url.endsWith(validUrl)) {

                value = true;

            }
        }
        return value;
    }

    // You also have to implement init() and destroy() methods.
}
