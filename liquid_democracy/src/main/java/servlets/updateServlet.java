/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gr.uoc.csd.hy359.liquid_democracy.db.UserDB;
import gr.uoc.csd.hy359.liquid_democracy.model.User;
import gr.uoc.csd.hy359.liquid_democracy.model.Validator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alivas
 */
public class updateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Enumeration<String> names = request.getParameterNames();
            String parname = names.nextElement();
            String value = request.getParameter(parname);
            String uid = (String) request.getSession().getAttribute("name");
            try {
                User u = UserDB.getUser(uid);
                Validator v = new Validator();
                switch (parname) {
                    case ("username"):
                        break;
                    case ("password"):
                        u.setPassword(value);
                        System.out.println(value);
                        break;
                    case ("email"):
                        v.eml.setValue(u.getEmail());
                        u.setEmail(value);
                        break;
                    case ("firstname"):
                        u.setFirstName(value);
                        break;
                    case ("lastname"):
                        u.setLastName(value);
                        break;
                    case ("occupation"):
                        u.setOccupation(value);
                        break;
                    case ("country"):
                        u.setCountry(value);
                        break;
                    case ("city"):
                        u.setTown(value);
                        break;
                    case ("address"):
                        u.setAddress(value);
                        break;
                    case ("dateOfBirth"):
                        u.setBirthDate(value);
                        break;
                    case ("gender"):
                        u.setGender(value);
                        break;
                    case ("interests"):
                        u.setInterests(value);
                        break;
                    case ("general"):
                        u.setInfo(value);
                        break;
                }
                String user = u.getUserName();
                v.uid.setValue(user);
                String pass1 = u.getPassword();
                v.pass1.setValue("123Abc!!");
                String pass2 = u.getPassword();
                v.pass2.setValue("123Abc!!");
                String address = u.getAddress();
                v.addr.setValue(address);
                String city = u.getTown();
                v.city.setValue(city);
                String dob = u.getBirthDate();
                v.dob.setValue(dob);
                String email = u.getEmail();
                v.eml.setValue(email);
                String fnm = u.getFirstName();
                v.fnm.setValue(fnm);
                String lnm = u.getLastName();
                v.lnm.setValue(lnm);
                String gen = u.getInfo();
                v.gen.setValue(gen);
                String intr = u.getInterests();
                v.intr.setValue(intr);
                String ctr = u.getCountry();
                v.ctr.setValue(ctr);
                String sex = u.getGender().toString();
                v.sex.setValue(sex);
                String ocp = u.getOccupation();
                v.ocp.setValue(ocp);
                if (!v.getValidity()) {
                    out.println("Wrong Input");
                } else {
                    UserDB.updateUser(u);
                }
            } catch (ClassNotFoundException e) {
                response.setStatus(500);
                response.resetBuffer();
                out.println("Internal Server Error");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
