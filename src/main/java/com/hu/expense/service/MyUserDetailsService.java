package com.hu.expense.service;

import com.hu.expense.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<com.hu.expense.entity.User> temp = userRepository.findById((String)s);
        String un = temp.get().getUsername();
        String p = temp.get().getPassword();


        return new User(un, p,
                new ArrayList<>());
    }
}
