/*
 * Author: Dennis Kalochristianakis (and Anastasis Livanidis, O:) )
 */
package servlets;

import gr.uoc.csd.hy359.liquid_democracy.db.InitiativeDB;
import gr.uoc.csd.hy359.liquid_democracy.db.UserDB;
import gr.uoc.csd.hy359.liquid_democracy.db.VoteDB;
import gr.uoc.csd.hy359.liquid_democracy.model.Initiative;
import gr.uoc.csd.hy359.liquid_democracy.model.Poll;
import gr.uoc.csd.hy359.liquid_democracy.model.User;
import gr.uoc.csd.hy359.liquid_democracy.model.Vote;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dennis
 * @author Alivas
 */
public class getInitServlet extends HttpServlet {

    /**
     * Returns Poll for closed initiatives
     *
     * @param initiatives the input initiative list
     * @return HashMap with Poll objects corresponding to closed initiatives in
     * the initiative list given
     * @throws ClassNotFoundException
     */
    private HashMap<Integer, Poll> getPoll(List<Initiative> initiatives) throws ClassNotFoundException {
        List<Vote> votes = VoteDB.getAllVotes();
        HashMap<Integer, Poll> initvotes = new HashMap<>();
        for (Initiative init : initiatives) { //For every initiative displayed
            if (init.getStatus() == 2) { //If it's closed
                Poll p = new Poll();
                for (Vote v : votes) { //Parse through all the votes
                    if (v.getInitiativeID() == init.getId()) { //If any vote's initid matches current initid
                        if (v.getVote() == 1) { //If an upvote
                            p.addUpv();
                        } else { //If a downvote
                            p.addDownv();
                        }
                    }
                }
                initvotes.put(init.getId(), p);
            }
        }
        return initvotes;
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
        boolean DEBUG = false;
        try (PrintWriter out = response.getWriter()) {
            List<Initiative> initiatives = new ArrayList<>(); //List of initiatives returned by DB
            String mode = request.getParameter("mode");
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("name"); //Username as displayed in the current session
            HashMap<Integer, Poll> initvotes = new HashMap<>();
            try {
                switch (mode) {
                    case "user":
                        InitiativeDB.applyExpiration();
                        //Returns user's own initiatives
                        initiatives = InitiativeDB.getInitiatives(username);
                        initvotes = getPoll(initiatives);
                        break;
                    case "active":
                        InitiativeDB.applyExpiration();
                        //returns all active initiatives
                        initiatives = InitiativeDB.getInitiativesWithStatus(1);
                        break;
                    case "ended":
                        InitiativeDB.applyExpiration();
                        //returns all ended initiatives
                        initiatives = InitiativeDB.getInitiativesWithStatus(2);
                        initvotes = getPoll(initiatives);
                        break;
                    case "add":
                        String category = request.getParameter("category");
                        String title = request.getParameter("title");
                        String desc = request.getParameter("description");
                        //create new initiative obj with user's data
                        Initiative intv = new Initiative(username, category, title, desc, 0);
                        InitiativeDB.addInitiative(intv); //Add initiative to DB
                        break;
                    case "delete": //deletes the initiative with the given id
                        int id = Integer.parseInt(request.getParameter("initid"));
                        InitiativeDB.deleteInitiative(id);
                        break;
                    case "update":
                        id = Integer.parseInt(request.getParameter("initid"));
                        category = request.getParameter("category");
                        title = request.getParameter("title");
                        desc = request.getParameter("description");
                        intv = InitiativeDB.getInitiative(id);
                        if (!category.equals("") && category.matches("[\\w]{4,20}")) {
                            intv.setCategory(category);
                        } else if (!category.equals("")) {
                            out.println("Error in Category: Format - 4 to 20 characters");
                            response.setStatus(400);
                        }
                        if (!title.equals("") && title.matches("[\\w]{4,20}")) {
                            intv.setTitle(title);
                        } else if (!title.equals("")) {
                            out.println("Error in Title: Format - 4 to 20 characters");
                            response.setStatus(400);
                        }
                        if (!desc.equals("") && desc.length() <= 5000) {
                            intv.setDescription(desc);
                        } else if (!desc.equals("")) {
                            out.println("Error in Description: Format - maximum 5000 characters");
                            response.setStatus(400);
                        }
                        InitiativeDB.updateInitiative(intv);
                        break;
                    case "activate":
                        id = Integer.parseInt(request.getParameter("initid"));
                        String date = request.getParameter("date");
                        String time = request.getParameter("time");
                        if (date.equals("")) {
                            date = "1000-10-10";
                        }
                        if (time.equals("")) {
                            time = "00:00";
                        }
                        int years = Integer.parseInt(date.substring(0, 4));
                        int months = Integer.parseInt(date.substring(5, 7));
                        int days = Integer.parseInt(date.substring(8, 10));
                        int hours = Integer.parseInt(time.substring(0, 2));
                        int minutes = Integer.parseInt(time.substring(3, 5));
                        Calendar today = Calendar.getInstance();
                        Calendar expDate = Calendar.getInstance();
                        expDate.set(years, months - 1, days, hours, minutes, 0);
                        if (expDate.before(today)) {
                            response.resetBuffer();
                            out.println("Date/Time is Invalid (maybe in the past?)");
                            response.setStatus(400);
                        } else {
                            Date dt = expDate.getTime();
                            intv = InitiativeDB.getInitiative(id);
                            if (intv.getStatus() == 0) { // check if initiative is non active
                                intv.setExpires(dt);
                                intv.setStatus(1); //activate initiatve
                                InitiativeDB.updateInitiative(intv); //update initiative in DB
                            } else {
                                response.resetBuffer();
                                response.setStatus(400);
                                out.print("Initiative already activated");
                            }
                        }
                        break;
                    case "search":
                        //searches for initiatives with the given title
                        String searchParam = request.getParameter("searchMode");
                        switch (searchParam) {
                            case "user":
                                InitiativeDB.applyExpiration();
                                initiatives = InitiativeDB.getInitiatives(username);
                                initvotes = getPoll(initiatives);
                                break;
                            case "active":
                                InitiativeDB.applyExpiration();
                                initiatives = InitiativeDB.getInitiativesWithStatus(1);
                                break;
                            case "ended":
                                InitiativeDB.applyExpiration();
                                initiatives = InitiativeDB.getInitiativesWithStatus(2);
                                initvotes = getPoll(initiatives);
                                break;
                            default:
                                response.setStatus(400);
                                out.print("Bad argument for search: " + searchParam);
                                break;
                        }

                        String searchQuery = request.getParameter("query");

                        for (Initiative i : new ArrayList<>(initiatives)) {
                            //check for matching query
                            //and remove initiative from list
                            //if it doesn't match

                            switch (searchParam) {
                                case "user":
                                    if (!(i.getTitle().contains(searchQuery))) {
                                        initiatives.remove(i);
                                    }
                                    break;
                                case "active":
                                case "ended":
                                    if (!(i.getTitle().contains(searchQuery)) && !(i.getCreator().equals(searchQuery))) {
                                        initiatives.remove(i);
                                    }
                                    break;
                                default:
                                    System.out.println("Error " + 400 + " bad argument: " + mode);
                                    response.resetBuffer();
                                    response.setStatus(400);
                                    out.print("Error " + 400 + " bad argument: " + mode);
                                    return;
                            }

                        }

                        break;
                    default:
                        System.out.println("Error " + 400 + " bad argument: " + mode);
                        response.resetBuffer();
                        response.setStatus(400);
                        out.print("Error " + 400 + " bad argument: " + mode);
                        return;
                }
                int j = -1; //Non-active initiative index
                int k = -1; //Active initiative index
                int l = -1; //Closed initiative index
                for (int i = 0; i < initiatives.size(); i++) { //for every initiative found return an html element containing data from the current initiative
                    if (initiatives.get(i).getTitle().contains("test") && !DEBUG) {
                        continue;
                    }
                    int status = initiatives.get(i).getStatus();
                    String initStatus = "";
                    String initEditForm = "";
                    String initActForm = "";
                    String initVoteArea = "";
                    //ArrayList<String> editors = new ArrayList<>();
                    switch (status) {
                        case 0: //non active initiative policy
                            //This was a brilliant idea so I left it there for possible future implemention: Click on Element to edit it
                            /*
                            editors.add("<input type = \"text\" class = \"initiative_title_edit\"  pattern = \"[\\w]{4,20}\" "
                                    + "onblur = \"updateInitiative('title', 'after')\"/>");
                            editors.add("<input type = \"text\" class = \"initiative_cat_edit\"  pattern = \"[\\w]{4,20}\" "
                                    + "onblur = \"updateInitiative('cat', 'after')\"/>");
                            editors.add("<textarea class =\"initiative_desc_text_edit\"  maxlength=5000 onblur = \"updateInitiative('desc_text', 'after')\"/>");
                            for (String e : editors) {
                                System.out.println(e);
                            }
                             */
                            j++;
                            initVoteArea += "<div class = \"vote_area\"><p>Non-Active</p></div>";
                            initStatus += "<input type = \"button\" value = \"Delete\" class=\"initDel\" onclick = \"sendAjaxPOSTdelinit(document.getElementById('id" + i + "'))\"/>";
                            initStatus += "<input type = \"button\" value = \"Edit\" class = \"initEdit\" onclick = \"togglerclass('initeditform', " + j + ")\"/>";
                            initStatus += "<input type = \"button\" value = \"Activate\" class=\"initAct\" onclick = \"togglerclass('initactform', " + j + ")\"/>";
                            initEditForm = "<form name =\"newinitform\" class =\"initeditform\">\n"
                                    + "                <hr/><label for =\"iid\"><b>Initiative Title</b></label><br/>\n"
                                    + "                     <input type =\"text\" placeholder = \"Enter Initiative Title\" class = \"iid\" pattern =\"[\\w]{4,20}\" name =\"inittitle\"/><br/>\n"
                                    + "                     <label for =\"icat\"><b>Initiative Category</b></label><br/>\n"
                                    + "                     <input type =\"text\" placeholder = \"Enter Initiative Category\" class =\"icat\" pattern =\"[\\w]{4,20}\" name =\"initcat\"/><br/>\n"
                                    + "                     <label for =\"idesc\"><b>Initiative Description</b></label><br/>\n"
                                    + "                     <textarea class =\"idesc\" maxlength=\"5000\" name =\"initdesc\"></textarea><br/>\n"
                                    + "                     <input type = \"button\" value =\"Confirm\" onclick =\"sendAjaxPOSTeditinit(" + i + ", " + j + ")\"/>\n"
                                    + "     </form>";

                            initActForm = "<form name =\"initActform\" class =\"initactform\">\n"
                                    + "                <hr/><label for = \"expdate\"><b>Enter expiration date</b></label>"
                                    + "\n                   <input type=\"date\" name = \"expdate\" class = \"initdate\" />"
                                    + "                     <input type=\"time\" name = \"exptime\" class = \"inittime\" />"
                                    + "                     <input type=\"button\" value = \"Activate\" onclick =\"sendAjaxPOSTactivate(" + i + ", " + j + ")\" />\n"
                                    + "     </form>";
                            break;
                        case 1: //active policy initiatives
                            k++;
                            String upmod = "";
                            String dnmod = "";
                            Vote usrVote = VoteDB.getUserVote(username, initiatives.get(i).getId());
                            if (usrVote != null) {
                                int vote = usrVote.getVote();
                                if (vote == 1) {
                                    upmod = "style = \"border: 2px solid #93d7ff; border-radius: 7px\"";
                                    dnmod = "style = \"border: 2px solid transparent\"";
                                } else {
                                    upmod = "style = \"border: 2px solid transparent\"";
                                    dnmod = "style = \"border: 2px solid #93d7ff; border-radius: 7px\"";
                                }
                            }
                            initVoteArea += "<div class = \"vote_area\"><div onclick = \"sendVote(" + i + ", " + k + ", 'true')\" class=\"initUpv\" " + upmod + "></div>";
                            initVoteArea += "<div onclick = \"sendVote(" + i + ", " + k + ", 'false')\" class = \"initDownv\" " + dnmod + "></div></div>";
                            initStatus += "<input type=\"text\" id=\"delegatortxt" + k +"\" name=\"delegatorName\">";
                            initStatus += " <button type=\"button\" id=\"delegatorbtn" + k +"\" onclick=\" addDelegatorPost(" +initiatives.get(i).getId() +", "+ k +")\">Add user as Delegator</button> ";
                            initStatus += "<div class = \"initStatus\"><p>Active Until: " + initiatives.get(i).getExpiresAsString() + "</p>";
                            initStatus += "<p>Made By: " + initiatives.get(i).getCreator() + "</p></div>";
                            break;
                        case 2: //ended policy initiatives
                            l++;
                            initVoteArea += "<div class = \"vote_area\"><p>Closed</p></div>";
                            initStatus += "<div class = \"vote_results\"><p>Upvotes: " + initvotes.get(initiatives.get(i).getId()).getUpv() + "</p>";
                            initStatus += "<p>Downvotes: " + initvotes.get(initiatives.get(i).getId()).getDownv() + "</p>";
                            initStatus += "<p>Result: " + (initvotes.get(initiatives.get(i).getId()).getResult() ? "Voted" : "Rejected") + "</p>";
                            initStatus += "<p>Made By: " + initiatives.get(i).getCreator() + "</p> </div>";                                    
                            break;
                        default:
                            System.out.println("Error " + 500 + " Invalid status in DB: ");
                            response.resetBuffer();
                            response.setStatus(500);
                            out.print("Error " + 500 + " Invalid status in DB: ");
                            return;
                    }
                    out.print("<div class = \"initiative\">\n"
                            + "            <div class =\"initiative_title\" onclick = \"document.getElementById('" + "initd" + i + "').style.height === '1.7em' ?\n"
                            + "             document.getElementById('" + "initd" + i + "').style.height = 'auto' :\n"
                            + "             document.getElementById('" + "initd" + i + "').style.height = '1.7em'\">\n"
                            + "                <p>" + initiatives.get(i).getTitle() + "</p>\n"
                            + "            </div>\n"
                            //+ (!editors.isEmpty() ? editors.remove(j++) : "")
                            + initVoteArea
                            + "            <div class = \"initiative_cat\">\n"
                            + "                <p>" + initiatives.get(i).getCategory() + "</p>\n"
                            + "            </div><br/>\n"
                            //+ (!editors.isEmpty() ? editors.remove(j++) : "")
                            + "            <div class = \"init_content\">"
                            + "                 <div class =\"initiative_desc\" id = \"initd" + i + "\">\n"
                            + "                     <div class = \"initiative_desc_text\">"
                            + initiatives.get(i).getDescription()
                            + "                     </div>"
                            //+ (!editors.isEmpty() ? editors.remove(j++) : "")
                            + "                     <br/><hr/>"
                            + "                     <div class =\"initiative_status\">\n"
                            + initStatus
                            + "                         <br/>"
                            + initEditForm
                            + initActForm
                            + "                     </div>\n"
                            + "                 </div>"
                            + "            </div>"
                            + "            <span class =\"initid\" id=\"id" + i + "\" >" + initiatives.get(i).getId() + "</span>"
                            + "</div>");
                }
            } catch (ClassNotFoundException e) {
                System.out.println("ERROR: " + e.getMessage());
                response.resetBuffer();
                response.setStatus(500);
                out.println("ERROR: " + e.getMessage());
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
