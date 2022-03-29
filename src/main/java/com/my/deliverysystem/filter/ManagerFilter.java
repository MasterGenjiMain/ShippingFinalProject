package com.my.deliverysystem.filter;

import com.my.deliverysystem.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebFilter("/manager/*")
public class ManagerFilter implements Filter {
    private final Logger logger = Logger.getLogger(ManagerFilter.class);
    private final int MANAGER_ROLE_ID = 2;

    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug("Filtering /manager/*");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String loginPath = File.separator + ((HttpServletRequest) servletRequest).getContextPath()
                .replace("/", "") + File.separator + "login.jsp";
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if (user.getRoleId() == MANAGER_ROLE_ID) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse) servletResponse).sendRedirect(loginPath);
            }
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(loginPath);
        }
    }
}
