package servlets;

import java.util.ArrayList;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class sessionListener implements HttpSessionListener {

    private static int numberOfUsersOnline;
    private static final ArrayList<String> userNames = new ArrayList<>();

    public sessionListener() {
        numberOfUsersOnline = 0;
    }

    public static int getNumberOfUsersOnline() {
        return numberOfUsersOnline;
    }

    public static ArrayList<String> getUsernames() {
        return userNames;
    }
    
    public static void addUser(String name) {
        userNames.add(name);
        System.out.println("Username saved " + name);
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {

        //System.out.println("Session created by Id : " + event.getSession().getId());
        synchronized (this) {
            numberOfUsersOnline++;
            //userNames.add((String) event.getSession().getAttribute("name"));
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {

        System.out.println("Session destroyed by Id : " + event.getSession().getId());
        synchronized (this) {
            numberOfUsersOnline--;
        }

    }

}
