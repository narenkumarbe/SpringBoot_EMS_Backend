package com.finforz.ems_backend.controller;

import com.finforz.ems_backend.entity.UserInfo;
import com.finforz.ems_backend.exception.ResourceNotFoundException;
import com.finforz.ems_backend.service.JwtService;
import com.finforz.ems_backend.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "JWT Authentication")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;



    @PostMapping("/addUser")
    public ResponseEntity<UserInfo> addUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role
    ) {

        UserInfo userInfo = UserInfo.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
        UserInfo savedUserInfo = userInfoService.addUser(userInfo);
        return new ResponseEntity<>(savedUserInfo, HttpStatus.CREATED);
    }
    @PostMapping("/authenticate")
    @Operation(summary = "JWT token Generator", description = "JWT token Generator")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Unauthorized Access"),
            @ApiResponse(responseCode = "404", description = "Entity not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Exception") })
    public String authenticateAndGetToken(
            @RequestParam String username,
            @RequestParam String password
    ) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(username);
        } else {
            throw new ResourceNotFoundException("Invalid username or password");
        }

    }
}
