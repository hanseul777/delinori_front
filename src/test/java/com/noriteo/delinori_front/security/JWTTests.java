package com.noriteo.delinori_front.security;

import com.noriteo.delinori_front.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class JWTTests {

    @Autowired
    JWTUtil jwtUtil;

    @Test
    public void testGenerate(){
        String jwtStr = jwtUtil.generateToken("user10");
        log.info(jwtStr);
    }

    @Test
    public void testValidate(){
        String str = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTAiLCJpYXQiOjE2MzY0NDAyODMsImV4cCI6MTYzNjQ0MDM0M30.y1AwWPoEc6wx0JkkfGtSFduxt7QoUlpfaeISrA_3akU";

        try{
            jwtUtil.validateToken(str);
        }catch (ExpiredJwtException ex){
            log.error("...expried...");
            log.error(ex.getMessage());
        }catch (JwtException ex){
            log.info("...jwtException...");
            log.error(ex.getMessage());
        }

    }

}
