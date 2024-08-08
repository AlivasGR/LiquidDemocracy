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
public class entryServlet extends HttpServlet {

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
                    String userName = (String) session.getAttribute("name");
                    User u = UserDB.getUser(userName);
                    String pass = u.getPassword();
                    out.println(userName + " " + pass);
                } catch (ClassNotFoundException e) {
                    response.setStatus(500);
                    response.resetBuffer();
                    out.println("Internal Server Error");
                }
            } else {
                //==============Check for persistent cookies====================
                Cookie[] cookies = request.getCookies();
                System.out.println("PERCOOKIE");
                boolean foundCookie = false;
                Cookie cookie = null;
                if (cookies != null) {
                    for (Cookie cookie1 : cookies) {
                        if (cookie1.getName().equals("LDCookie")) {
                            cookie = cookie1;
                        }         
                        foundCookie = true;
                    }
                }

                //Retrieve cookie
                if (foundCookie && cookie != null) {
                    try {
                        System.out.println("FOUND COOKIE");
                        String userName = cookie.getValue();
                        User u = UserDB.getUser(userName);
                        String pass = u.getPassword();
                        out.println(userName + " " + pass); //return user credentials
                        cookie.setMaxAge(30 * 60); //refresh cookie expiration
                        response.addCookie(cookie);
                        return;
                    } catch (ClassNotFoundException e) {
                        response.setStatus(500);
                        response.resetBuffer();
                        out.println("Internal Server Error");
                    }

                }
                //==============================================================

                response.setStatus(400);
                out.println("No previous session");
                System.out.println("No previous session");
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
