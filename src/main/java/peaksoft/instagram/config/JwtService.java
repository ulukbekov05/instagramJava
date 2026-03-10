//package peaksoft.instagram.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import peaksoft.instagram.entity.User;
//
//import java.time.ZonedDateTime;
//import java.util.NoSuchElementException;
//
//import static org.springframework.security.config.Elements.JWT;
//
//@Component
//@RequiredArgsConstructor
//public class JwtService {
//
//    @Value("${security.secret.key}")
//    private String security;
//    // create token
//    public String generateToken(User user){
//        Algorithm algorithm=Algorithm.HMAC256(security);
//
//        return JWT.create()
//                .withClaim("email", user.getEmail())
//                .withClaim("password", user.getPassword())  //
//                .withIssuedAt(ZonedDateTime.now().toInstant())
//                .withExpiresAt(ZonedDateTime.now().plusDays(1).toInstant())
//                .sign(algorithm);
//
//    }
//
//    public User verifyToken(String token){
//        Algorithm algorithm = Algorithm.HMAC256(security);
//        JWTVerifier verifier = JWT.require(algorithm).build();
//        DecodedJWT decodedJWT= verifier.verify(token);
//        String email = decodedJWT.getClaim("email").asString();
//        return userRepo.findByEmail(email).orElseThrow(
//                ()-> new NoSuchElementException(String.format("User with email: %s doesn`t exists",email))
//        );
//    }
//}
