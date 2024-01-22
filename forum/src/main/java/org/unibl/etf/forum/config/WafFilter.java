package org.unibl.etf.forum.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class WafFilter extends OncePerRequestFilter {
    private final TokenBlacklist tokenBlacklist;
    private final JwtService jwtService;
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
            "('.+--)|(--)|(\\|)|(%7C)|(\\'\\s*OR\\s*\\d\\s*=\\s*\\d\\s*(--|#))|(\\b\\d+\\s+OR\\s+\\d+\\s*=\\s*\\d+\\s*(--|#))",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern XSS_PATTERN = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static final int BUFFER_OVERFLOW_LIMIT = 10000;
    private static final Logger LOGGER = LoggerFactory.getLogger(WafFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
        if (isRequestMalicious(wrappedRequest)) {
            String jwt = extractJwtFromRequest(request);
            if (jwt != null) {
                tokenBlacklist.blacklistToken(jwt);
                String subject = extractSubjectFromJwt(jwt);
                    LOGGER.warn("JWT blacklisted due to malicious request. Subject: {}, JWT: {}", subject, jwt);
            }
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Malicious input detected");
            return;
        }

        filterChain.doFilter(wrappedRequest, response);
    }

    private String extractSubjectFromJwt(String jwt) {
        try {
            Claims claims = jwtService.extractAllClaims(jwt);
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isRequestMalicious(HttpServletRequest request) {
        return hasSqlInjection(request) || hasXss(request) || hasBufferOverflow(request) ||
                hasMaliciousHeaders(request) || hasMaliciousCookies(request) || hasMaliciousBody(request);
    }

    private boolean hasSqlInjection(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramValue = request.getParameter(params.nextElement());
            if (SQL_INJECTION_PATTERN.matcher(paramValue).find()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasXss(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramValue = request.getParameter(params.nextElement());
            if (XSS_PATTERN.matcher(paramValue).find()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasBufferOverflow(HttpServletRequest request) {
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramValue = request.getParameter(params.nextElement());
            if (paramValue.length() > BUFFER_OVERFLOW_LIMIT) {
                return true;
            }
        }
        return false;
    }

    private boolean hasMaliciousHeaders(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            String headerValue = request.getHeader(headerName);
            if (isMaliciousValue(headerValue)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasMaliciousCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (isMaliciousValue(cookie.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean hasMaliciousBody(HttpServletRequest request) {
        try {
            ServletInputStream inputStream = request.getInputStream();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return isMaliciousValue(body);
        } catch (IOException e) {
            throw new RuntimeException("Error reading request body", e);
        }
    }

    private boolean isMaliciousValue(String value) {
        return SQL_INJECTION_PATTERN.matcher(value).find() || XSS_PATTERN.matcher(value).find() || value.length()>BUFFER_OVERFLOW_LIMIT;
    }

}