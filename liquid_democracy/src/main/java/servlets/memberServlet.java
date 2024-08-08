package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gr.uoc.csd.hy359.liquid_democracy.model.*;
import gr.uoc.csd.hy359.liquid_democracy.db.*;

/**
 *
 * @author Alivas
 */
public class memberServlet extends HttpServlet {

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
            if (request.getQueryString() != null) { //GET
                if (request.getParameterMap().containsKey("username")) {
                    try {
                        if (!UserDB.checkValidUserName(request.getParameter("username"))) {
                            out.println("Username not available :( ");
                        } else {
                            response.resetBuffer();
                        }
                    } catch (ClassNotFoundException e) {
                        String errmsg = "Error in Database";
                        response.setStatus(500);
                        response.resetBuffer();
                        out.println(errmsg);
                    }
                } else if (request.getParameterMap().containsKey("email")) {
                    try {
                        if (!UserDB.checkValidEmail(request.getParameter("email"))) {
                            out.println("e-mail is not available :( ");
                        } else {
                            response.resetBuffer();
                        }
                    } catch (ClassNotFoundException e) {
                        String errmsg = "Error in Database";
                        response.setStatus(500);
                        response.resetBuffer();
                        out.println(errmsg);
                    } catch (Exception e) {
                        response.setStatus(400);
                        response.resetBuffer();
                        out.println(e.getMessage());
                    }
                } else if (request.getParameterMap().containsKey("password")) {
                    try {
                        String name = (String) request.getSession(false).getAttribute("name");
                        User u = UserDB.getUser(name);
                        if (!request.getParameter("password").equals(u.getPassword())) {
                            out.println("This isn't your old password");
                        } else {
                            response.resetBuffer();
                        }
                    } catch (ClassNotFoundException e) {
                        String errmsg = "Error in Database";
                        response.setStatus(500);
                        response.resetBuffer();
                        out.println(errmsg);
                    } catch (Exception e) {
                        response.setStatus(400);
                        response.resetBuffer();
                        out.println(e.getMessage());
                    }
                }
            } else { //POST
                Validator v = new Validator();
                String uid = request.getParameter("username");
                v.uid.setValue(uid);
                String pass1 = request.getParameter("pass1");
                v.pass1.setValue("123Abc!!");
                v.pass2.setValue("123Abc!!");
                String address = request.getParameter("address");
                v.addr.setValue(address);
                String city = request.getParameter("city");
                v.city.setValue(city);
                String dob = request.getParameter("dateOfBirth");
                v.dob.setValue(dob);
                String email = request.getParameter("email");
                v.eml.setValue(email);
                String fnm = request.getParameter("firstName");
                v.fnm.setValue(fnm);
                String lnm = request.getParameter("lastName");
                v.lnm.setValue(lnm);
                String gen = request.getParameter("general");
                v.gen.setValue(gen);
                String intr = request.getParameter("interests");
                v.intr.setValue(intr);
                String ctr = request.getParameter("country");
                v.ctr.setValue(ctr);
                String sex = request.getParameter("gender");
                v.sex.setValue(sex);
                String ocp = request.getParameter("occupation");
                v.ocp.setValue(ocp);
                if (!v.getValidity()) {
                    String errmsg = "Invalid Fields: ";
                    for (Input f : v.invalidFields) {
                        errmsg += "\n" + f.getName();
                    }
                    response.setStatus(400);
                    response.resetBuffer();
                    out.println(errmsg);
                } else {
                    User nu = new User(uid, email, pass1, fnm, lnm, dob, ocp, ctr, city, address, intr, gen, sex);
                    if (nu.getUserName().equals("AlivasGR") || nu.getUserName().equals("DennisKa")) {
                        //add admin
                    }
                    try {
                        nu.checkFields();
                        UserDB.addUser(nu);
                        out.println(nu.toString());
                    } catch (ClassNotFoundException e) {
                        String errmsg = "Error in Database";
                        response.setStatus(500);
                        response.resetBuffer();
                        out.println(errmsg);
                    } catch (Exception e) {
                        String errmsg = "Error in form fields";
                        response.setStatus(400);
                        response.resetBuffer();
                        out.println(errmsg);
                    }
                }
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
