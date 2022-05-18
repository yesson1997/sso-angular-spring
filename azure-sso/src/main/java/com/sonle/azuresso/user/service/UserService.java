package com.sonle.azuresso.user.service;

import com.sonle.azuresso.security.exception.CustomException;
import com.sonle.azuresso.security.jwt.JwtTokenProvider;
import com.sonle.azuresso.user.domain.UserAndToken;
import com.sonle.azuresso.user.domain.UserRole;
import com.sonle.azuresso.user.entity.User;
import com.sonle.azuresso.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public UserAndToken signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username).get();
            String token = jwtTokenProvider.createToken(username, user.getUserRoles());
            return new UserAndToken(user, token);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public UserAndToken singup(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            String token = jwtTokenProvider.createToken(user.getUsername(), user.getUserRoles());
            return new UserAndToken(user, token);
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserAndToken processSigninForSso(String email) {
        User user = userRepository.findByEmail(email).orElseGet(() -> userRepository.save(createUserForSso(email)));
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getUserRoles());
        return new UserAndToken(user, token);
    }

    private User createUserForSso(String email) {
        User user = new User();
        user.setUsername(email);
        user.setUserRoles(Arrays.asList(UserRole.ROLE_CLIENT));
        user.setEmail(email);
        user.setPassword(generateRandomPassword());
        return user;
    }

    // https://www.baeldung.com/java-random-string
    private String generateRandomPassword() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public User whoami(HttpServletRequest request) {
        String username = jwtTokenProvider.getUsername(request);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("Can not found user with username: " + username, HttpStatus.BAD_REQUEST));
    }
}
