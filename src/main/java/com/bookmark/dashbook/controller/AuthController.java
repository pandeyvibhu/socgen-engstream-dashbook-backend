package com.bookmark.dashbook.controller;

import com.bookmark.dashbook.filter.JwtUtil;
import com.bookmark.dashbook.model.auth.AuthenticationRequest;
import com.bookmark.dashbook.model.auth.AuthenticationResponse;
import com.bookmark.dashbook.model.dto.GenericMessageDto;
import com.bookmark.dashbook.model.dto.GroupContextResponseListDto;
import com.bookmark.dashbook.service.MyUserDetailsService;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericMessageDto> register(
            @RequestBody User user) {
        userDetailsService.signupUser(user);

        return ResponseEntity.ok(new GenericMessageDto("User Created"));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}