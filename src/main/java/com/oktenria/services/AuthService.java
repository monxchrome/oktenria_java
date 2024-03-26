package com.oktenria.services;

import com.oktenria.dto.ResponseDto;
import com.oktenria.entities.User;
import com.oktenria.exception.AuthException;
import com.oktenria.exception.UserException;
import com.oktenria.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private static final long ACCESS_TOKEN_TTL_SECONDS = 3000;

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

    public Map<String, String> loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException("Invalid email or password");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String access = jwtService.generateAccess(authentication, ACCESS_TOKEN_TTL_SECONDS);
        String refresh = jwtService.generateRefresh(authentication, REFRESH_TOKEN_TTL_SECONDS);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", access);
        tokens.put("refreshToken", refresh);

        return tokens;
    }
}
