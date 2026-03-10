package peaksoft.instagram.service.serviceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.instagram.config.jwt.JwtService;
import peaksoft.instagram.dto.auth.AuthResponse;
import peaksoft.instagram.dto.auth.SignInRequest;
import peaksoft.instagram.dto.auth.SignUpRequest;
import peaksoft.instagram.entity.Follower;
import peaksoft.instagram.entity.User;
import peaksoft.instagram.repository.UserRepo;
import peaksoft.instagram.service.AuthService;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;




    @Override
    @Transactional
    public AuthResponse sighUp(SignUpRequest signUpRequest) {

        if(userRepo.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new BadCredentialsException(
                    String.format("user with email %s already exists", signUpRequest.getEmail()));}
        User user = User.builder()
                .userName(signUpRequest.getUserName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phoneNumber(signUpRequest.getPhoneNumber())
                .email(signUpRequest.getEmail())
                .build();

        Follower follower = Follower.builder()
                .user(user)
                .build();

        user.setFollower(follower);
        userRepo.save(user);

        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .email(user.getEmail())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse signIn(SignInRequest signInRequest) {
        User user = userRepo.findByEmail(signInRequest.getEmail())
                .orElseThrow(() ->
                        new NoSuchElementException(String.format("User with email %s not found", signInRequest.getEmail()))
                );
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())){
            throw new BadCredentialsException("wrong userName or password");}
        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .email(user.getEmail())
                .token(token)
                .build();
    }
}

