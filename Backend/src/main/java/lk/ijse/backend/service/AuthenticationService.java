package lk.ijse.backend.service;


import lk.ijse.backend.auth.request.SignInRequest;
import lk.ijse.backend.auth.request.SignUpRequest;
import lk.ijse.backend.auth.response.JWTAuthResponse;

public interface AuthenticationService {
    JWTAuthResponse signIn(SignInRequest signInRequest);

    JWTAuthResponse signUp(SignUpRequest signUpRequest);
}
