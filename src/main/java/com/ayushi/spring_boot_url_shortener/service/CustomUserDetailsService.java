package com.ayushi.spring_boot_url_shortener.service;

import com.ayushi.spring_boot_url_shortener.model.User;
import com.ayushi.spring_boot_url_shortener.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository users;

    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // hashed bcrypt
                .roles(user.getRole() == null ? "USER" : user.getRole())
                .build();
    }
}
