package com.noriteo.delinori_front.security.filter;

import com.noriteo.delinori_front.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class TokenCheckFilter extends OncePerRequestFilter {

    private JWTUtil jwtUtil;

    public TokenCheckFilter(JWTUtil jwtUtil){this.jwtUtil = jwtUtil;}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("=========TokenCheckFilter=========");

        String path = request.getRequestURI();
        log.info(path);

        if (path.startsWith("/api/")){
            String authToken = request.getHeader("Authorization");
            if (authToken == null){ //토큰 없는 경우
                log.info("authToken is null");

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                // json 리턴
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", "403");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
                return;
            }
            //jwt 검사
            String tokenStr = authToken.substring(7);

            try{
                jwtUtil.validateToken(tokenStr);
            }catch (ExpiredJwtException ex){ // 만료 된 토큰
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // json 리턴
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "EXPIRED API TOKEN.. TOO OLD";
                json.put("code", "401");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
                return;

            }catch (JwtException jex){ // 잘못 된 토큰
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // json 리턴
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "YOUR ACCESS TOKEN IS INVALID";
                json.put("code", "401");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                out.close();
                return;
            }

            filterChain.doFilter(request, response);

        }else {
            log.info("=========TokenCheckFilter=========");
            filterChain.doFilter(request, response);
        }
    }
}
