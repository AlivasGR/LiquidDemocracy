/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gr.uoc.csd.hy359.liquid_democracy.db.DelegatedDB;
import gr.uoc.csd.hy359.liquid_democracy.db.VoteDB;
import gr.uoc.csd.hy359.liquid_democracy.model.Delegated;
import gr.uoc.csd.hy359.liquid_democracy.model.Vote;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dennis
 */
public class VoteServlet extends HttpServlet {

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
            boolean voteFlag = Boolean.parseBoolean(request.getParameter("vote"));
            int initid = Integer.parseInt(request.getParameter("initid"));
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("name"); //Username as displayed in the current session
            try {
                Vote vote = VoteDB.getUserVote(username, initid);
                if (vote == null) {     //user hasn't voted yet for this initiative
                    vote = new Vote(username, "", initid, voteFlag, true);
                    VoteDB.addVote(vote);
                } else {                         //user has already voted for this policy
                   vote.setVote(voteFlag, true); //therefore we update his existing vote
                   VoteDB.updateVote(vote);
                }
                
                //Delegator voting
                List<Delegated> delegatedList = DelegatedDB.getDelegated(initid, username);
                
                for(int i = 0; i < delegatedList.size(); i++){
                    //get each delegated user's vote
                    Vote deleVote =  VoteDB.getUserVote(delegatedList.get(i).getUserName(),initid); 
                    
                    if(deleVote ==  null){
                        //delegated user hasn't voted
                        deleVote = new Vote(delegatedList.get(i).getUserName(), username, initid, voteFlag, false);
                        VoteDB.addVote(deleVote);
                        //DEBUG
                        System.out.println("User " + username + " voted " + voteFlag + " as delegator for user "+ delegatedList.get(i).getUserName());
                    }else{
                        //delegated user has already voted
                        int votedByFlag = deleVote.getVotedBy();
                        if(votedByFlag == 0){//delegator has voted this init
                            vote.setVote(voteFlag, false);
                            VoteDB.updateVote(vote);
                            //DEBUG
                            System.out.println("User " + username + " updated vote " + voteFlag + " as delegator for user "+ delegatedList.get(i).getUserName());
                        }
                    }
                }
                
                
            } catch (ClassNotFoundException e) {
                System.out.println("Error in Database: " + e.getMessage());
                response.setStatus(500);
                out.print("Error in Database: " + e.getMessage());
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
