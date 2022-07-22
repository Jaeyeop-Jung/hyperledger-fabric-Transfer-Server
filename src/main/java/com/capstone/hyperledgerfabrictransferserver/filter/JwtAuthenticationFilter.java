package com.capstone.hyperledgerfabrictransferserver.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

//        String token = null;
//        if(httpRequest.getHeader("Authorization") != null && httpRequest.getHeader("Authorization").startsWith("Bearer ")){
//            token = httpRequest.getHeader("Authorization").split(" ")[1];
//        }
//
//        if( token != null && jwtTokenProvider.validateToken(token) ) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(token); // 만약 null이 리턴되면 role 확인을 못하니까 EntryPoint로 넘어간다. role 확인이되면 deniedhandler로 간다.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }

        String findIdentifier = jwtTokenProvider.findIdentifierByHttpServletRequest(httpRequest);
        if (findIdentifier != null) {
            Authentication authentication = jwtTokenProvider.getAuthenticationByIdentifier(findIdentifier);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(httpRequest, httpResponse);
    }
}
