package by.gruca.cafe.controller.filter;

import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/moderator/*")
public class ModeratorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Object role = httpServletRequest.getSession().getAttribute("role");
        if (role.toString().equals(Role.MODERATOR.getRoleValue()) || role.toString().equals(Role.ADMIN.getRoleValue())) {
            chain.doFilter(request, response);
        } else {
            RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(UrlsEnum._MAIN.getPage());
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
