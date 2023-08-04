package com.wanted.backend.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailService {
    UserDetails loadUserByUsername(String email);
    UserDetails loadUserById(Long id);

}
