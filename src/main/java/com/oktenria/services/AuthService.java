package com.oktenria.services;

import com.oktenria.dto.ResponseDto;
import com.oktenria.entities.User;
import com.oktenria.exception.UserException;
import com.oktenria.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private static final long ACCESS_TOKEN_TTL_SECONDS = 30;

    private static final long REFRESH_TOKEN_TTL_SECONDS = 7 * 24 * 60 * 60;


    public void registerUser(User user) throws UserException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserException("User with email " + user.getEmail() + " already exists");
        }

        if (userRepository.findByPhone(user.getPhone()) != null) {
            throw new UserException("User with phone number " + user.getPhone() + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public Map<String, String> loginUser(String email, String password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String access = jwtService.generateAccessToken(authentication, ACCESS_TOKEN_TTL_SECONDS);
        String refresh = jwtService.generateRefreshToken(authentication, REFRESH_TOKEN_TTL_SECONDS);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", access);
        tokens.put("refreshToken", refresh);

        return tokens;
    }
}
