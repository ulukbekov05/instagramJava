package peaksoft.instagram.service;

import peaksoft.instagram.dto.SimpleResponse;
import peaksoft.instagram.dto.auth.AuthResponse;
import peaksoft.instagram.dto.auth.ResetPasswordRequest;
import peaksoft.instagram.dto.auth.SignInRequest;
import peaksoft.instagram.dto.auth.SignUpRequest;

public interface AuthService {
    AuthResponse sighUp(SignUpRequest signUpRequest);

    AuthResponse signIn(SignInRequest signInRequest);

    SimpleResponse forgotPassword(String email);
    SimpleResponse resetPassword(ResetPasswordRequest request);
}
