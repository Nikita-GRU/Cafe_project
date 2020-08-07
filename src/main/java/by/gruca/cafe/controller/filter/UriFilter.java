package by.gruca.cafe.controller.filter;


import by.gruca.cafe.controller.UrlsEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class UriFilter implements Filter {
    Logger logger = LogManager.getLogger(UriFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        request.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(true);
        RequestDispatcher dispatcher;
        String requestURI = req.getRequestURI();
        String command = req.getParameter("command");

//        for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()
//        ) {
//            logger.info(entry.getKey() + "/" + Arrays.toString(entry.getValue()));
//        }
//        String baseUrl = req.getRequestURI() + "?" + req.getQueryString();
//        request.setAttribute("baseUrl", baseUrl);

        for (UrlsEnum url : UrlsEnum.values()
        ) {
            String requestUrl = req.getContextPath() + url.getUrl();
            if (req.getRequestURI().equals(requestUrl)) {
                dispatcher = req.getRequestDispatcher(url.getPage());
                response.setCharacterEncoding("UTF-8");
                dispatcher.forward(request, response);
                return;
            }
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
