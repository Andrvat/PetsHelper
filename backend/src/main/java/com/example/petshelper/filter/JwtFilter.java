package com.example.petshelper.filter;

import com.example.petshelper.model.UserCredential;
import com.example.petshelper.provider.JwtProvider;
import com.example.petshelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";
    private static final String BEGIN_HEADER = "Bearer ";
    private static final String ADMIN = "Admin";

    private final JwtProvider jwtProvider;
    private final UserService service;

    public JwtFilter(JwtProvider jwtProvider, UserService service) {
        this.jwtProvider = jwtProvider;
        this.service = service;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("do filter...");
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateAuthToken(token)) {
            String userEmail = jwtProvider.getEmailFromAuthToken(token);
            UserCredential customUserCredentialDetails =  service.findByEmail(userEmail);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserCredentialDetails, null, customUserCredentialDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith(BEGIN_HEADER)) {
            return bearer.substring(BEGIN_HEADER.length());
        }
        return null;
    }
}