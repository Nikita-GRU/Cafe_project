package by.gruca.cafe.filter;


import by.gruca.cafe.command.UrlsEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class UriFilter implements Filter {
    Logger logger = LogManager.getLogger(UriFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(true);
        RequestDispatcher dispatcher;
        String requestURI = req.getRequestURI();
        String command = req.getParameter("command");
        if (command == null || command.isEmpty()) {
            for (UrlsEnum url : UrlsEnum.values()
            ) {
                if (req.getRequestURI().equals(req.getContextPath() + "/" + url.toString().toLowerCase())) {
                    dispatcher = req.getRequestDispatcher(url.getPage());
                    dispatcher.forward(request, response);
                    return;
                }
            }
        } else {
            dispatcher = req.getRequestDispatcher("/controller?command=" + command);
            dispatcher.forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
