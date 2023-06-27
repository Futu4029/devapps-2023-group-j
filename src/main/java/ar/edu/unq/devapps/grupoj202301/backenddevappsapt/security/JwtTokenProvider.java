package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final Long expTimeMillis;

    public JwtTokenProvider(@Value("${app.security.jwt.secret-key}") String secretKey,
                            @Value("${app.security.expiration-time}") Long expTimeMillis){
        this.secretKey = secretKey;
        this.expTimeMillis = expTimeMillis;
    }

    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date now = new Date();
        Date expirationToken = new Date(now.getTime() + expTimeMillis);

        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationToken)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }

    public String getEmailFromJwt (String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception ignored) {}
        return false;
    }
}
