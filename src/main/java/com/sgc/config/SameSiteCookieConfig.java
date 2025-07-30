package com.sgc.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class SameSiteCookieConfig implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        chain.doFilter(req, res);

        if (res instanceof HttpServletResponse response) {
            HttpServletRequest request = (HttpServletRequest) req;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("JSESSIONID".equals(cookie.getName())) {
                        String value = cookie.getValue();
                        response.setHeader("Set-Cookie",
                                String.format("JSESSIONID=%s; Path=/; HttpOnly; Secure; SameSite=None", value));
                    }
                }
            }
        }
    }
}
