package peaksoft.instagram.service.serviceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.instagram.config.jwt.JwtService;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.auth.AuthResponse;
import peaksoft.instagram.dto.auth.ResetPasswordRequest;
import peaksoft.instagram.dto.auth.SignInRequest;
import peaksoft.instagram.dto.auth.SignUpRequest;
import peaksoft.instagram.entity.Follower;
import peaksoft.instagram.entity.User;
import peaksoft.instagram.repository.UserRepo;
import peaksoft.instagram.service.AuthService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JavaMailSender mailSender;



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




    @Override
    public SimpleResponse forgotPassword(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("email %s табылган жок", email)));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepo.save(user);

        String link = "http://localhost:8080/api/auth/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Паролду калыбына келтирүү");
        message.setText(
                "Паролду өзгөртүү үчүн төмөнкү ссылка аркылуу өтүңүз:\n"
                        + link +
                        "\n\nСсылка 15 мүнөт иштейт."
        );

        mailSender.send(message);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Ссылка " + email + " га жөнөтүлдү")
                .build();
    }

    @Override
    public SimpleResponse resetPassword(ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadCredentialsException("Паролдор дал келбейт");
        }

        User user = userRepo.findByResetToken(request.getToken())
                .orElseThrow(() -> new NoSuchElementException("Токен жараксыз"));

        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            user.setResetToken(null);
            user.setResetTokenExpiry(null);
            userRepo.save(user);
            throw new BadCredentialsException("Токен жараксыз же мөөнөтү бүттү");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepo.save(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пароль өзгөртүлдү")
                .build();
    }





}

