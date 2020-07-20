package by.gruca.cafe.controller.filter;

import by.gruca.cafe.model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class RoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Object role = httpServletRequest.getSession().getAttribute("role");
        if (role == null) {
            httpServletRequest.getSession().setAttribute("role", Role.GUEST.getRoleValue());
        }
        chain.doFilter(httpServletRequest, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
