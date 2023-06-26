package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {
    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date now = new Date();
        Date expirationToken = new Date(now.getTime() + SecurityConstants.JWT_EXPIRATION_TOKEN);

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationToken)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_FIRM)
                .compact();
        return token;
    }

    public String getEmailFromJwt (String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_FIRM)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWT_FIRM).parseClaimsJws(token);
            return true;
        } catch (Exception ignored) {}
        return false;
    }
}
