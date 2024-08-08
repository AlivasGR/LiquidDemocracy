/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gr.uoc.csd.hy359.liquid_democracy.db.UserDB;
import gr.uoc.csd.hy359.liquid_democracy.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alivas
 */
public class adminServlet extends HttpServlet {

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
            HttpSession session = request.getSession(false);
            if (session != null) {
                try {
                    String name = (String) session.getAttribute("name");
                    String req = request.getParameter("command");
                    switch (req) {
                        case ("deleteUser"):
                            String usrnm = request.getParameter("username");
                            //String email = request.getParameter("email");
                            UserDB.deleteUser(usrnm);
                            out.println("User " + usrnm + " was successfully deleted from the database");
                            break;
                        case ("addUser"):
                            usrnm = request.getParameter("username");
                            usrnm = "NPC_" + usrnm;
                            String eml = request.getParameter("email");
                            String ocp = request.getParameter("occupation");
                            User u = new User();
                            u.setUserName(usrnm);
                            u.setPassword("123Abc!!");
                            u.setEmail(eml);
                            u.setFirstName("John");
                            u.setLastName("Dooe");
                            u.setOccupation(ocp);
                            u.setCountry("GR");
                            u.setTown("Athens");
                            u.setGender("Unknown");
                            u.setBirthDate("1970-01-01");
                            UserDB.addUser(u);
                            out.println("User " + usrnm + " was successfully added to the database");
                            break;
                        case ("makeAdmin"): //admin not in db so this doesn't work
                            usrnm = request.getParameter("username");
                            User ur = UserDB.getUser(usrnm);
                            UserDB.updateUser(ur);
                            out.println("User " + usrnm + " was successfully promoted to admin!");
                            break;
                        case ("deleteInit"):
                            String mode = request.getParameter("delmode");
                            switch (mode) {
                                case ("Title"):
                                    //mariadb has integrity constraint for deletion of closed inits
//                                    String initid = request.getParameter("initid");
//                                    List<Initiative> init = InitiativeDB.getAllInitiatives();
//                                    for (Initiative i : init) {
//                                        if (i.getTitle().equals(initid)) {
//                                            InitiativeDB.deleteInitiative(i.getId());
//                                        }
//                                    }
//                                    out.println("Initiative with Title: " + initid + " was successfully deleted from the database");
                                    break;
                                case ("ID"):
                                    //mariadb has integrity constraint for deletion of closed inits
//                                    int iid = Integer.parseInt(request.getParameter("initid"));
//                                    InitiativeDB.deleteInitiative(iid);
//                                    out.println("Initiative with ID: " + iid + " was successfully deleted from the database");
                                    break;
                                default:
                                    response.setStatus(400);
                                    out.println("This mode for delete initiative is not available");
                            }
                            break;
                        default:
                            out.println("Invalid command");
                            response.setStatus(400);
                    }
                } catch (ClassNotFoundException e) {
                    response.sendError(500);
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
