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
            for (Cookie cookie : ((HttpServletRequest) req).getCookies() != null ? ((HttpServletRequest) req).getCookies() : new Cookie[0]) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    Cookie sameSiteCookie = new Cookie("JSESSIONID", value);
                    sameSiteCookie.setMaxAge(cookie.getMaxAge());
                    sameSiteCookie.setPath("/");
                    sameSiteCookie.setSecure(true);
                    sameSiteCookie.setHttpOnly(true);
                    response.setHeader("Set-Cookie", String.format("JSESSIONID=%s; Path=/; HttpOnly; Secure; SameSite=None", value));
                }
            }
        }
    }
}
