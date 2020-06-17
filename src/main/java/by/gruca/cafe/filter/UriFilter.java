package by.gruca.cafe.filter;


import by.gruca.cafe.command.UrlsEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class UriFilter implements Filter {
    Logger logger = LogManager.getLogger(UriFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        RequestDispatcher dispatcher;
        String requestURI = req.getRequestURI();
        logger.info(((HttpServletRequest) request).getMethod());
        logger.info("reqURI=" + req.getRequestURI());

        for (UrlsEnum url : UrlsEnum.values()
        ) {
            if (requestURI.equals(req.getContextPath() + "/" + url.toString().toLowerCase())) {
                dispatcher = request.getRequestDispatcher(url.getPage());
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
