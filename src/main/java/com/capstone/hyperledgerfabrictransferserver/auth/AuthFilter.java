package com.capstone.hyperledgerfabrictransferserver.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    /**
     * methodName : doFilter
     * author : Jaeyeop Jung
     * description :
     *
     * step0. /user/*, /admin/*이 아닌 경우 인증&인가 없이 진행 (로그인, 회원가입, /actuator)
     * step1. request에서 Token 빼내기
     * step2. token 인증
     * step3. token 권한별 인가
     *
     * @param request  the request
     * @param response the response
     * @param chain    the chain
     * @throws IOException      the io exception
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("utf-8");

        // step0
        if (httpServletRequest.getRequestURI().startsWith("/actuator")
            || (httpServletRequest.getRequestURI().equals("/user/login") && httpServletRequest.getMethod().equals("GET"))
            || (httpServletRequest.getRequestURI().equals("/admin/login") && httpServletRequest.getMethod().equals("GET"))
            || (httpServletRequest.getRequestURI().equals("/user/user") && httpServletRequest.getMethod().equals("POST"))
        ) {

            chain.doFilter(httpServletRequest, httpServletResponse);

        } else {

            // step1
            String token = null;
            if (httpServletRequest.getHeader("Authorization") != null && httpServletRequest.getHeader("Authorization").startsWith("Bearer ")) {
                token = httpServletRequest.getHeader("Authorization").split(" ")[1];
            }

            // step2
            if (!jwtTokenProvider.validateToken(token)) {
                log.error("잘못된 토큰으로 페이지 요청 token = " + token);

                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Map<String, String> map = new HashMap<>();
                map.put("errortype", "UNAUTHORIZED");
                map.put("code", "401");
                map.put("message", "잘못된 토큰으로 접근하였습니다. 다시 로그인 해주세요");

                response.getWriter().write(objectMapper.writeValueAsString(map));
                return;
            }

            // step3
            String requestedRole = jwtTokenProvider.findUserRoleByToken(token);
            if (httpServletRequest.getRequestURI().startsWith("/admin")
                && (requestedRole.equals("ROLE_STUDENT") || requestedRole.equals("ROLE_STOREMANAGER"))
            ) {
                log.error("잘못된 권한으로 페이지 요청 UserRole = " + requestedRole);

                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> map = new HashMap<>();
                map.put("errortype", "FORBIDDEN");
                map.put("code", "403");
                map.put("message", "잘못된 권한으로 접근하였습니다.");

                response.getWriter().write(objectMapper.writeValueAsString(map));
                return;
            }

            chain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

}
