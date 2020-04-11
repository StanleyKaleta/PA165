package cz.muni.fi.pa165.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebFilter("/*")
public class YearFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        SimpleDateFormat format = new SimpleDateFormat("yyyy", request.getLocale());
        request.setAttribute("year", format.format(new Date()));

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
