package peaksoft.instagram.service;

import peaksoft.instagram.dto.auth.AuthResponse;
import peaksoft.instagram.dto.auth.SignInRequest;
import peaksoft.instagram.dto.auth.SignUpRequest;

public interface AuthService {
    AuthResponse sighUp(SignUpRequest signUpRequest);

    AuthResponse signIn(SignInRequest signInRequest);
}
