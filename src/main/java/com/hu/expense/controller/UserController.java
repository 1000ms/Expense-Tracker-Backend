package com.hu.expense.controller;

import com.hu.expense.dto.stats;
import com.hu.expense.entity.AuthenticationRequest;
import com.hu.expense.entity.AuthenticationResponse;
import com.hu.expense.entity.User;
import com.hu.expense.repositories.UserRepository;
import com.hu.expense.service.MyUserDetailsService;
import com.hu.expense.util.GetUsernameFromJwt;
import com.hu.expense.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private GetUsernameFromJwt getUsernameFromJwt;


    @GetMapping("/hello")
    public User hello(@RequestHeader(value="Authorization") AuthenticationResponse authenticationResponse){
        String username = getUsernameFromJwt.getUsername(authenticationResponse);
        System.out.println(username);
        return userRepository.findById(username).get();
    }


    @GetMapping("/get-user")
    @ApiOperation(value = "Get User Details", response = User.class, authorizations = { @Authorization(value="jwtToken") })
    public User getUser(@RequestHeader(value="Authorization") AuthenticationResponse authenticationResponse){
        String username = getUsernameFromJwt.getUsername(authenticationResponse);
        return userRepository.findById(username).get();
    }


    @PostMapping("/signup")
    @ApiOperation(value = "Sign-Up", response = AuthenticationResponse.class)
    public ResponseEntity<?> singUp(@RequestBody User user){
        User temp = userRepository.save(user);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(temp.getUsername(), temp.getPassword())
            );
        }
        catch (BadCredentialsException e) {
//            throw new Exception("Incorrect username or password", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(temp.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }


    @PostMapping("/login")
    @ApiOperation(value = "Login", response = AuthenticationResponse.class)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            System.out.println("noo");
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }





}
