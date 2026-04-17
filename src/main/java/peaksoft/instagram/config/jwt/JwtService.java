package peaksoft.instagram.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import peaksoft.instagram.entity.User;
import peaksoft.instagram.repository.UserRepo;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;


@Component
@RequiredArgsConstructor
public class JwtService {

    private final UserRepo userRepo;

    @Value("${security.secret.key}")
    private String security;

    public String generateToken(User user){
        Algorithm algorithm=Algorithm.HMAC256(security);

        return JWT.create()
                .withClaim("email", user.getEmail())
                .withClaim("password", user.getPassword())  //
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusDays(1).toInstant())
                .sign(algorithm);

    }

    public User verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(security);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT= verifier.verify(token);
        String email = decodedJWT.getClaim("email").asString();
        return userRepo.findByEmail(email).orElseThrow(
                ()-> new NoSuchElementException(String.format("User with email: %s doesn`t exists",email))
        );
    }


    public User checkAuthentication(){
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();
        return   userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                String.format("User with email %s not found", email)));
    }
}
