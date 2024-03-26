package com.oktenria.controllers;

import com.oktenria.dto.RefreshDto;
import com.oktenria.dto.ResponseDto;
import com.oktenria.entities.LoginForm;
import com.oktenria.entities.User;
import com.oktenria.exception.AuthException;
import com.oktenria.exception.UserException;
import com.oktenria.services.AuthService;
import com.oktenria.services.JwtService;
import com.oktenria.services.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private static final long ACCESS_TOKEN_TTL_SECONDS = 30;

    private static final long REFRESH_TOKEN_TTL_SECONDS = 7 * 24 * 60 * 60;

    private final AuthService authService;

    private final JwtService jwtService;

    @PostMapping("api/auth/login")
    public ResponseEntity<ResponseDto> loginUser(@RequestBody LoginForm loginForm) {
        try {
            String email = loginForm.getEmail();
            String password = loginForm.getPassword();

            Map<String, String> tokens = authService.loginUser(email, password);

            ResponseDto responseDto = new ResponseDto();
            responseDto.setAccessToken(tokens.get("accessToken"));
            responseDto.setRefreshToken(tokens.get("refreshToken"));

            return ResponseEntity.ok(responseDto);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("api/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid User user) {
        try {
            authService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("api/auth/refresh")
    public ResponseEntity<ResponseDto> refreshAccessToken(@RequestBody RefreshDto refreshDto, Authentication authentication) {
        try {
            String refreshToken = refreshDto.getRefreshToken();

            Date refreshTokenExpirationDate = jwtService.extractExpiration(refreshToken);

            if (refreshTokenExpirationDate.before(new Date())) {
                throw new AuthException("Refresh token has expired");
            }

            String newAccessToken = jwtService.generateAccess(authentication, ACCESS_TOKEN_TTL_SECONDS);

            String newRefreshToken = jwtService.generateRefresh(authentication, REFRESH_TOKEN_TTL_SECONDS);

            ResponseDto responseDto = new ResponseDto();
            responseDto.setAccessToken(newAccessToken);
            responseDto.setRefreshToken(newRefreshToken);

            return ResponseEntity.ok(responseDto);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
