package peaksoft.instagram.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.auth.*;
import peaksoft.instagram.service.AuthService;


@Controller  // ← @RestController эмес
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationApi {
     private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseBody
    public AuthResponse SignUp(@RequestBody @Valid SignUpRequest signUpRequest){
        return authService.sighUp(signUpRequest);
    }

    @PostMapping("/signIn")
    @ResponseBody
    public AuthResponse signIn(@Valid @RequestBody SignInRequest signInRequest){
        return authService.signIn(signInRequest);
    }

    @PostMapping("/forgot-password")
    @ResponseBody
    public SimpleResponse forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return authService.forgotPassword(request.getEmail());
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public SimpleResponse resetPassword(@RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(Model model) {
        return "reset-password"; // HTML рендер болот
    }


}
