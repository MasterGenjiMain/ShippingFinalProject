package com.my.deliverysystem.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebFilter("/user/*")
public class UserFilter implements Filter {
    private final Logger logger = Logger.getLogger(UserFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug("Filtering /user/*");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) servletRequest).getSession();

        if (session.getAttribute("user") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String loginPath = File.separator + ((HttpServletRequest) servletRequest).getContextPath()
                    .replace("/", "") + File.separator + "login.jsp";
            ((HttpServletResponse) servletResponse).sendRedirect(loginPath);
        }
    }
}
