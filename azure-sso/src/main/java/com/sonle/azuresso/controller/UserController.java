package com.sonle.azuresso.controller;

import com.sonle.azuresso.controller.reqres.*;
import com.sonle.azuresso.sso.service.AzureSSOService;
import com.sonle.azuresso.user.domain.UserAndToken;
import com.sonle.azuresso.user.entity.User;
import com.sonle.azuresso.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AzureSSOService azureSSOService;

    public UserController(UserService userService, AzureSSOService azureSSOService) {
        this.userService = userService;
        this.azureSSOService = azureSSOService;
    }

    @PostMapping("/signin")
    public ResponseEntity<SuccessAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(makeSuccessAuthenticationResponse(userService.signin(signinRequest.getUsername(), signinRequest.getPassword())));
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessAuthenticationResponse> signup(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(makeSuccessAuthenticationResponse(userService.singup(createUserFromSingupRequest(signupRequest))));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GetUsersResponse> getUsers() {
        return ResponseEntity.ok(makeGetUsersResponse(userService.getUsers()));
    }

    private User createUserFromSingupRequest(SignupRequest request) {
        return new User(request.getUsername(), request.getEmail(), request.getPassword(), request.getUserRoles());
    }

    private GetUsersResponse makeGetUsersResponse(List<User> users) {
        return new GetUsersResponse(users.stream()
                .map(user -> new PublicUser(user.getUsername(), user.getEmail(), user.getUserRoles()))
                .collect(Collectors.toList())
        );
    }

    private SuccessAuthenticationResponse makeSuccessAuthenticationResponse(UserAndToken userAndToken) {
        User user = userAndToken.getUser();
        PublicUser publicUser = new PublicUser(user.getUsername(), user.getEmail(), user.getUserRoles());
        return new SuccessAuthenticationResponse(publicUser, userAndToken.getToken());
    }

    @GetMapping("/me")
    public ResponseEntity<PublicUser> whoami(HttpServletRequest request) {
        User user = userService.whoami(request);
        return ResponseEntity.ok(makePublicUser(user));
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity.ok(username);
    }

    @GetMapping("/auth_url")
    public ResponseEntity<?> getAuthenticationUrl() {
        return ResponseEntity.ok(new GetAuthUrlResponse(azureSSOService.buildAuthenticationUrl()));
    }

    @PostMapping("/callback")
    public ResponseEntity<?> handleSsoRedirection(@RequestParam MultiValueMap<String, String> formData) {
        String authCode = formData.getFirst("code");
        String redirectUrlWithToken = azureSSOService.handleSigninForSsoUser(authCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrlWithToken))
                .build();
    }


    private PublicUser makePublicUser(User user) {
        return new PublicUser(user.getUsername(), user.getEmail(), user.getUserRoles());
    }
}
