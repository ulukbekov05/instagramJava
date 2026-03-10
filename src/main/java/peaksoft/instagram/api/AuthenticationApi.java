package peaksoft.instagram.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.instagram.dto.auth.AuthResponse;
import peaksoft.instagram.dto.auth.SignInRequest;
import peaksoft.instagram.dto.auth.SignUpRequest;
import peaksoft.instagram.service.AuthService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationApi {
    private final AuthService authService;


    @PostMapping("/sign-up")
    public AuthResponse SignUp(@RequestBody @Valid SignUpRequest signUpRequest){
        return authService.sighUp(signUpRequest);
    }

    @PostMapping("/signIn")
    public AuthResponse signIn(@Valid @RequestBody SignInRequest signInRequest){
        return authService.signIn(signInRequest);
    }

}
