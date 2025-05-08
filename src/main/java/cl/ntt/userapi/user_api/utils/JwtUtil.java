package cl.ntt.userapi.user_api.utils;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    @Value("${jwt.roles}")
    private String rolesProperty;

    @Value("${jwt.secret}")
    private String secretProperty;

    @Value("${jwt.expiration.time}")
    private long expirationTimeProperty;

    @Value("${jwt.issuer}")
    private String issuerProperty;

    private static String secret;
    private static long expirationTime;
    private static String issuer;
    private static List<String> roles;

    @PostConstruct
    public void init() {
        secret = secretProperty;
        expirationTime = expirationTimeProperty;
        issuer = issuerProperty;
        roles = List.of(rolesProperty.split(","));
    }

    public String generateToken(String subject) {

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(subject)
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            return verifier.verify(token).getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

}
