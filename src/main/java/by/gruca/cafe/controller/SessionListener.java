package by.gruca.cafe.controller;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

public class SessionListener implements HttpSessionActivationListener {
    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {

    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        se.getSession().setAttribute("username", "guest");
        se.getSession().setAttribute("role", "guest");
    }
}
