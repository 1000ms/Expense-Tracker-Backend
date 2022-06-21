package com.hu.expense.util;

import com.hu.expense.entity.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUsernameFromJwt {

    @Autowired
    private JwtUtil jwtUtil;

    public String getUsername(AuthenticationResponse authenticationResponse){

        String fullJwt = authenticationResponse.getJwt();
        String jwt = fullJwt.substring(7,fullJwt.length());
        String username = jwtUtil.extractUsername(jwt);
        return username;
    }
}
