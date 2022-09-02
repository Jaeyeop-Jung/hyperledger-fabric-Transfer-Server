package com.capstone.hyperledgerfabrictransferserver.filter;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.EmptyTokenException;
import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectTokenException;
import com.capstone.hyperledgerfabrictransferserver.domain.Admin;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
//test
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final Long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L;


    /**
     * methodName : generateJwtToken
     * author : Jaeyeop Jung
     * description : User 도메인 객체를 이용해 JWT 토큰 발행
     *
     * @param user the user
     * @return the jwt token
     */
    public String generateJwtToken(User user){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "ACCESS_TOKEN")
                .setHeaderParam("alg", "HS256")
                .setSubject(user.getIdentifier())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("UserRole", user.getUserRole().toString())
                .compact();
    }

    public String generateJwtToken(Admin admin) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "ACCESS_TOKEN")
                .setHeaderParam("alg", "HS256")
                .setSubject(admin.getEmail())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("UserRole", "ROLE_ADMIN")
                .compact();
    }

    /**
     * methodName : findUserIdByJwt
     * author : Jaeyeop Jung
     * description : JWT토큰으로 User 도메인 ID를 찾음
     *
     * @param httpServletRequest the token
     * @return the user id by jwt
     */
    public String findIdentifierByHttpServletRequest(HttpServletRequest httpServletRequest){
        if (httpServletRequest.getHeader("Authorization") == null || !httpServletRequest.getHeader("Authorization").startsWith("Bearer ")) {
            return null;
        }
        String token = httpServletRequest.getHeader("Authorization").split(" ")[1];
        if (!validateToken(token)){
            return null;
        }

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String findUserRoleByToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("UserRole").toString();
    }

    /**
     * methodName : validateToken
     * author : Jaeyeop Jung
     * description : JWT 토큰을 검증
     *
     * @param token the token
     * @return the token
     */
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (NullPointerException ex){
            log.error("JWT RefreshToken is empty");
        }
        return false;
    }

}
