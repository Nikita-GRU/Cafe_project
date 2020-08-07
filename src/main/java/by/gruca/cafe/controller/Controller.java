package by.gruca.cafe.controller;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.command.ActionCommand;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    Logger logger = LogManager.getLogger(Controller.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        String page = null;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        page = command.execute(request);


        if (page != null) {
            if (request.getMethod().equals(HttpMethods.POST)) {
                response.setCharacterEncoding("UTF-8");
                response.sendRedirect(page);
            } else {
                if (request.getDispatcherType() != DispatcherType.FORWARD)
                    page = UrlManager.getProperty("path.page.main");

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                logger.info("reqURL=" + request.getRequestURL());
                response.setCharacterEncoding("UTF-8");
                dispatcher.forward(request, response);
            }

        }
    }
}
