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
import javax.servlet.http.Cookie;

/**
 *
 * @author Alivas
 */
public class loginServlet extends HttpServlet {

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
            String name = request.getParameter("username");
            String password = request.getParameter("password").trim();
            try {
                User res = UserDB.getUser(name);
                if (res.getUserName().equals("")) {
                    throw new IllegalArgumentException("Username not Found");
                }
                String up = res.getPassword();
                if (!password.equals(up)) {
                    throw new IllegalArgumentException("Password is Incorrect");
                }
                HttpSession session = request.getSession(false);
                if (session == null) {
                    session = request.getSession(true);
                    session.setAttribute("name", name);
                    sessionListener.addUser(name);
                    System.out.println("Username saved");
                    session.setMaxInactiveInterval(60);
                }

                //==================Peristent Cookie===========================
                Cookie[] cookies = request.getCookies();
                boolean foundCookie = false;

                //check for existing cookie
                if (cookies != null) {
                    for (Cookie cookie1 : cookies) {
                        if (cookie1.getName().equals("LDCookie")) {
                            foundCookie = true;
                        }
                    }
                }
                //if not found create a new cookie!
                if (!foundCookie) {
                    Cookie cookie1 = new Cookie("LDCookie", name);
                    cookie1.setMaxAge(30 * 60);
                    response.addCookie(cookie1);
                    System.out.println("Created new cookie!");
                }

                //=================================================================
                out.println("<input type = \"button\" id = \"logoutmini\" value = \"Logout\" onclick = \"sendAjaxPOSTlogout()\"/>");
                out.println("<p onclick =\"getProfile()\">" + name + "</p>");
                User u = UserDB.getUser(name);
                if (u.getUserName().equals("AlivasEN") || u.getUserName().equals("Aristotelis")) {
                    out.println("<input type = \"button\" class = \"adminelement\" id =\"adminbutton\" value = \"Do It\" onclick = \"sendAdminRequest()\"/>");
                    out.println("<input type = \"text\" class = \"adminelement\" id = \"adminconsole\" placeholder = \"Admin Console\"/>");
                }
                if (!sessionListener.getUsernames().contains(name)) {
                  
                }
            } catch (ClassNotFoundException e) {
                response.setStatus(500);
                response.resetBuffer();
                out.println("Internal Server Error");
            } catch (IllegalArgumentException e) {
                response.setStatus(400);
                response.resetBuffer();
                out.println(e.getMessage());
            } catch (Exception e) {
                response.setStatus(400);
                response.resetBuffer();
                out.println(e.getMessage());
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
