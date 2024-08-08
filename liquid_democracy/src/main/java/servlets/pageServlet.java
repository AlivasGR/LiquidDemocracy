package servlets;

import gr.uoc.csd.hy359.liquid_democracy.db.UserDB;
import gr.uoc.csd.hy359.liquid_democracy.model.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alivas
 */
@WebServlet(name = "pageServlet", urlPatterns = {"/pageServlet"})
public class pageServlet extends HttpServlet {

    protected String getActiveUsers(String unm) throws ClassNotFoundException {
        ArrayList<String> aunm = sessionListener.getUsernames();
        ArrayList<User> allUsers = (ArrayList<User>) UserDB.getUsers();
        String res = "";
        for (User u : allUsers) {
            if (!unm.equals(u.getUserName()) && aunm.contains(u.getUserName())) {
                res += "<p class = \"aid\">" + u.getUserName() + "</p>";
            }
        }
        return res;
    }

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
            BufferedReader br;
            HttpSession session = request.getSession(false);
            ServletContext context = getServletContext();
            if (request.getQueryString() != null) {
                String file = request.getParameter("page");
                try {
                    InputStream is = context.getResourceAsStream("/pages/" + file);
                    if (is != null) {
                        InputStreamReader fr = new InputStreamReader(is);
                        br = new BufferedReader(fr);
                        String sCurrentLine;
                        while ((sCurrentLine = br.readLine()) != null) {
                            out.println(sCurrentLine);
                        }
                        if (file.startsWith("mc") && session != null) {
                            out.println("<p id = \"active_user_area\">Active Users: " + sessionListener.getNumberOfUsersOnline() + "<br/>"
                               + "You, and these other people:</br>"  + getActiveUsers((String)session.getAttribute("name")) + "</p>");
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    response.setStatus(500);
                    response.resetBuffer();
                    out.println("Internal Server Error");
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
